package scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._


class ThreadsTest extends Simulation {

  val httpConf = http
    .baseURL("http://192.168.56.30:8000/testedApplication/")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val cpuFree = scenario("Cpu Free").exec(http("call").get("/wait?sleepTime=5000"))
  val cpuExhausting = scenario("Cpu Exhausting").pause(60 second).exec(http("call").get("/task"))

  setUp(
    cpuFree.inject(rampUsers(180) over(30 seconds)),
    cpuExhausting.inject(rampUsers(60) over(60 seconds))
  ).protocols(httpConf)
}