package scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class FrequentlyChangedModel extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8080/testedApplication/")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val scn = scenario("FrequentlyChangedModel")
    .exec(http("update_33751").post("/updateById?id=33751&newName=Foo"))
    .exec(http("findById_33751").get("/findById?id=33751"))
    .exec(http("update_33756").post("/updateById?id=33756&newName=Foo"))
    .exec(http("findById_33756").get("/findById?id=33756"))
    .exec(http("update_33782").post("/updateById?id=33782&newName=Foo"))
    .exec(http("findById_33782").get("/findById?id=33782"))

  setUp(
    scn.inject(atOnceUsers(10))
  ).protocols(httpConf)
}