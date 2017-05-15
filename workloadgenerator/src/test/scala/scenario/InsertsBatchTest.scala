package scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class InsertsBatchTest extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8000/testedApplication/")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val scn1 = scenario("Inserts Low")
    .exec(http("call").put("/add").queryParam("categoryName", "newCategory"))

  val scn2 = scenario("Inserts Intensive").pause(60 second)
    .exec(http("call").put("/add").queryParam("categoryName", "newCategory"))
  setUp(
    scn1.inject(rampUsers(60) over(60 seconds)),
    scn2.inject(rampUsers(660) over(60 seconds))
  ).protocols(httpConf)
}