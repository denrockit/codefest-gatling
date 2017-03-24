package models

case class News(id: Option[Int], title: String, description: String)

object News {

	import play.api.libs.json._

	implicit val jsonFormat: OFormat[News] = Json.format[News]
}
