package computerdatabase // 1

import io.gatling.core.Predef._ // 2
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulation extends Simulation { // 3

  val httpConf = http // 4
    .baseURL("http://localhost:8080/testedApplication/") // 5
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // 6

  val scn = scenario("BasicSimulation") // 7
    .exec(http("request_1")  // 8
    .get("/findById?id=33751")) // 9
    .pause(5) // 10

  setUp( // 11
    scn.inject(atOnceUsers(5)) // 12
  ).protocols(httpConf) // 13
}