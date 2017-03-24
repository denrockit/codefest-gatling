package dao

import javax.inject.Inject

import models.News
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.Future

class NewsDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
	extends HasDatabaseConfigProvider[JdbcProfile] {

	import dbConfig.profile.api._

	private val newsQuery = TableQuery[NewsTable]

	def all(offset: Int, limit: Int): Future[Seq[News]] =
		db.run(newsQuery.sortBy(_.id).drop(offset).take(limit).result)

	def byId(id: Int): Future[Option[News]] =
		db.run(newsQuery.filter(_.id === id).result.headOption)

	def create(item: News): Future[Int] =
		db.run((newsQuery returning newsQuery.map(_.id)) += item)

	def update(item: News): Future[Boolean] =
		db.run(newsQuery.filter(_.id === item.id).map(n => (n.title, n.description)).update(item.title, item.description)).map { rows => rows == 1 }

	def delete(id: Int): Future[Boolean] =
		db.run(newsQuery.filter(_.id === id).delete).map { rows => rows == 1 }

	private class NewsTable(tag: Tag) extends Table[News](tag, "news") {

		def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

		def title: Rep[String] = column[String]("title")

		def description: Rep[String] = column[String]("description")

		override def * : ProvenShape[News] = (id.?, title, description) <> ((News.apply _).tupled, News.unapply)
	}

}
