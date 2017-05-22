package scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._


class CombinedTest extends Simulation {

  val httpConf = http
    .baseURL("http://192.168.56.30:8000/testedApplication/")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val cpuFree = scenario("Cpu Free").pause(60 second).exec(http("call").get("/wait?sleepTime=5000"))
  val intensiveInserts = scenario("Inserts Intensive").exec(http("call").get("/add").queryParam("categoryName", "newCategory"))

  setUp(
    cpuFree.inject(rampUsers(60) over(60 seconds)),
    intensiveInserts.inject(rampUsers(310) over(30 seconds))

  ).protocols(httpConf)
}