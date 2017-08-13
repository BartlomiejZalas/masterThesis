package scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class CacheTest extends Simulation {

  val httpConf = http
    .baseURL("http://192.168.56.30:8080/testedApplication/")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val mutable = scenario("MutableTraffic")
    .exec(http("findById").get("/find/194114"))
    .exec(http("update").post("/update/194114/?newName=Foo"))

  val immutable = scenario("ImmutableTraffic").pause(60 second)
    .exec(http("findById").get("/find/194114"))

  setUp(
    mutable.inject(rampUsers(60) over(60 seconds)),
    immutable.inject(rampUsers(60) over(60 seconds))
  ).protocols(httpConf)
}