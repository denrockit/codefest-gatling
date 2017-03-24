import Conf._
import Requests._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

object Scenario {

	val httpConf: HttpProtocolBuilder = http
		.baseURL(baseUrl)
		.extraInfoExtractor(e => List(
			e.request.getUrl,
			e.response.statusCode.getOrElse(" "),
			e.response.timings.responseTime
		))

	def scn(loopCount: Int = 1): ScenarioBuilder = scenario("CodeFest")
		.repeat(loopCount) {
			randomSwitch(
				(50, newsList),
				(35, newsItem),
				(5, updateNews),
				(5, createAndDeleteNews) // In fact multiplied by 2 requests.
			)
		}
}
