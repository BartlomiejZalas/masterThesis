package scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._


class CombinedTest extends Simulation {

  val httpConf = http
    .baseURL("http://192.168.56.30:8000/testedApplication/")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val cpuFree = scenario("Cpu Free").exec(http("call").get("/wait?sleepTime=5000"))
  val cpuExhausting = scenario("Cpu Exhausting").pause(60 second).exec(http("call").get("/task"))

  val lowInserts = scenario("Inserts Low").exec(http("call").put("/add").queryParam("categoryName", "newCategory"))
  val intensiveInserts = scenario("Inserts Intensive").pause(40 second).exec(http("call").put("/add").queryParam("categoryName", "newCategory"))

  setUp(
    cpuFree.inject(rampUsers(180) over(30 seconds)),
    cpuExhausting.inject(rampUsers(60) over(60 seconds)),
    lowInserts.inject(rampUsers(40) over(40 seconds)),
    intensiveInserts.inject(rampUsers(660) over(60 seconds))
  ).protocols(httpConf)
}