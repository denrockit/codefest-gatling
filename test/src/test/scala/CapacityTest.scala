import Scenario._
import io.gatling.core.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps

class CapacityTest extends Simulation {

	val asserts = Seq(
		global.requestsPerSec.gte(200),
		global.failedRequests.percent.lte(0.1)
	)

	val injectionSteps = rampUsers(8) over (15 seconds)

	setUp(scn(1000000).inject(injectionSteps).protocols(httpConf))
		.maxDuration(30 seconds)
		.assertions(asserts)
}
