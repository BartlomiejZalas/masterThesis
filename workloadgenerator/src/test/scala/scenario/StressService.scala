package scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class StressService extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8080/testedApplication/")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val scn = scenario("FrequentlyChangedModel")
    .exec(http("call").get("/task"))
  setUp(
    scn.inject(atOnceUsers(100))
  ).protocols(httpConf)
}