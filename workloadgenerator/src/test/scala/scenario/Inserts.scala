package scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class Inserts extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8000/testedApplication/")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val scn = scenario("Inserts")
    .exec(http("call").put("/add").queryParam("categoryName", "newCategory"))
  setUp(
    scn.inject(rampUsers(20) over(5 seconds))
  ).protocols(httpConf)
}