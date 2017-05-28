package scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._


class Random1Test extends Simulation {

  val httpConf = http
    .baseURL("http://192.168.56.30:8000/testedApplication/")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val find =     scenario("Find")     .pause(156 second).exec(http("call").get("/find/194114"))
  val update =   scenario("Update")   .pause(60 second).exec(http("call").get("/update/194114?newName=Foo"))
  val insert =   scenario("Inserts")  .pause(83 second).exec(http("call").get("/add").queryParam("categoryName", "newCategory"))
  val waitTask = scenario("Wait Task").pause(3 second).exec(http("call").get("/wait?sleepTime=5000"))
  val cpuTask =  scenario("Cpu Task") .pause(184 second).exec(http("call").get("/task"))

  setUp(
    find    .inject(rampUsersPerSec(5) to 6 during(249 seconds)),
    update  .inject(rampUsersPerSec(4) to 6 during(537 seconds)),
    insert  .inject(rampUsersPerSec(5) to 5 during(500 seconds)),
    waitTask.inject(rampUsersPerSec(2) to 5 during(305 seconds)),
    cpuTask .inject(rampUsersPerSec(0) to 1 during(80 seconds))
  ).protocols(httpConf)
}