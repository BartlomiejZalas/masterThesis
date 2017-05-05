package scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class CacheTest extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8000/testedApplication/")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val mutable = scenario("MutableTraffic")
    .exec(http("update_33751").post("/update/194114/?newName=Foo"))

  val immutable = scenario("ImmutableTraffic").pause(60 second)
    .exec(http("findById_33751").get("/find/194114"))

  setUp(
    mutable.inject(rampUsers(60) over(60 seconds)),
    immutable.inject(rampUsers(60) over(60 seconds))
  ).protocols(httpConf)
}