import Conf._
import io.gatling.core.feeder.RecordSeqFeederBuilder
import io.gatling.jdbc.Predef._

object Feeders {

	private val newsIdsQuery = s"""SELECT id FROM news LIMIT $feederSize"""
	private val newsQuery = s"""SELECT id, title FROM news ORDER BY id LIMIT $feederSize"""

	private def fromQuery(query: String) =
		jdbcFeeder(dbUrl, dbUser, dbPassword, query).circular

	val newsIds: RecordSeqFeederBuilder[Any] = fromQuery(newsIdsQuery)
	val news: RecordSeqFeederBuilder[Any] = fromQuery(newsQuery)
}
