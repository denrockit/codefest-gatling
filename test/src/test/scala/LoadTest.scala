import Scenario._
import io.gatling.core.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps

class LoadTest extends Simulation {

	val asserts = Seq(
		global.requestsPerSec.gte(80),
		global.failedRequests.count.is(0),
		global.responseTime.percentile3.lte(10),
		details("NewsList").responseTime.percentile3.lte(10),
		details("NewsItem").responseTime.percentile3.lte(10),
		details("CreateNews").responseTime.percentile3.lte(50),
		details("UpdateNews").responseTime.percentile3.lte(60),
		details("DeleteNews").responseTime.percentile3.lte(10)
	)

	val injectionSteps = Seq(
		rampUsersPerSec(1) to 100 during (15 seconds),
		constantUsersPerSec(100) during (30 seconds)
	)

	setUp(scn().inject(injectionSteps).protocols(httpConf))
		.assertions(asserts)
}
