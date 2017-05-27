package scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._


class Random1Test extends Simulation {

  val httpConf = http
    .baseURL("http://192.168.56.30:8000/testedApplication/")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val find =     scenario("Find")     .pause(582 second).exec(http("call").get("/find/194114"))
  val update =   scenario("Update")   .pause(54 second).exec(http("call").get("/update/194114?newName=Foo"))
  val insert =   scenario("Inserts")  .pause(471 second).exec(http("call").get("/add").queryParam("categoryName", "newCategory"))
  val waitTask = scenario("Wait Task").pause(300 second).exec(http("call").get("/wait?sleepTime=5000"))
  val cpuTask =  scenario("Cpu Task") .pause(171 second).exec(http("call").get("/task"))

  setUp(
    find    .inject(rampUsersPerSec(1) to 4 during(3 seconds)),
    update  .inject(rampUsersPerSec(4) to 4 during(530 seconds)),
    insert  .inject(rampUsersPerSec(3) to 3 during(64 seconds)),
    waitTask.inject(rampUsersPerSec(2) to 5 during(101 seconds)),
    cpuTask .inject(rampUsersPerSec(1) to 1 during(203 seconds))
  ).protocols(httpConf)
}