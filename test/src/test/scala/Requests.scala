import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

object Requests {

	val newsList: ChainBuilder = exec(
		http("NewsList")
			.get("/news")
			.check(status.is(200))
	)

	val newsItem: ChainBuilder = feed(Feeders.newsIds).exec(
		http("NewsItem")
			.get("/news/${id}")
			.check(status.is(200))
	)

	val updateNews: ChainBuilder = feed(Feeders.news)
		.exec(session =>
			session.set("description", UUID.randomUUID.toString))
		.exec(
			http("UpdateNews")
				.put("/news/${id}")
				.body(ElFileBody("news.json")).asJSON
				.check(status.is(204))
		)

	val createAndDeleteNews: ChainBuilder =
		exec(session =>
			session
				.set("title", UUID.randomUUID.toString)
				.set("description", UUID.randomUUID.toString))
		.exec(
			http("CreateNews")
				.post("/news")
				.body(ElFileBody("news.json")).asJSON
				.check(status.is(201))
				.check(jsonPath("$.id").saveAs("id"))
		)
		.doIf(session => session("id").asOption[String].isDefined) {
			exec(
				http("DeleteNews")
					.delete("/news/${id}")
					.check(status.is(204))
			)
		}
}
