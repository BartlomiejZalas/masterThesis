package scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._


class ThreadsTest extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8051/testedApplication/")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val cpuFree = scenario("Cpu Free").exec(http("call").get("/wait?sleepTime=5000"))
  val cpuExhausting = scenario("Cpu Exhausting").pause(45 second).exec(http("call").get("/task"))

  setUp(
    cpuFree.inject(atOnceUsers(600)),
    cpuExhausting.inject(rampUsers(110) over(60 seconds))
  ).protocols(httpConf)
}