package controllers

import dao.NewsDAO
import javax.inject.Inject
import models.News
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc.{Action, AnyContent, InjectedController}

import scala.concurrent.{ExecutionContext, Future}

class NewsController @Inject()(protected val newsDAO: NewsDAO)(implicit ec: ExecutionContext)
	extends InjectedController {

	def list(offset: Option[Int], limit: Option[Int]): Action[AnyContent] = Action.async {
		newsDAO.all(offset.getOrElse(0), limit.getOrElse(20)).map(r => Ok(Json.toJson(r)))
	}

	def item(id: Int): Action[AnyContent] = Action.async {
		newsDAO.byId(id).map {
			case Some(n) => Ok(Json.toJson(n))
			case None => NotFound
		}
	}

	def create(): Action[JsValue] = Action.async(parse.json) { request =>
		request.body.validate[News]
			.fold(
				errors =>
					Future(BadRequest(Json.obj("errors" -> JsError.toJson(errors)))),
				news => {
					newsDAO.create(news).map { id =>
						Created(Json.toJson(news.copy(id = Some(id))))
					}
				}
			)
	}

	def update(id: Int): Action[JsValue] = Action.async(parse.json) { request =>
		request.body.validate[News]
			.fold(
				errors =>
					Future(BadRequest(Json.obj("errors" -> JsError.toJson(errors)))),
				news => {
					newsDAO.update(news.copy(id = Some(id))).map {
						case true => NoContent
						case false => InternalServerError
					}
				}
			)
	}

	def delete(id: Int): Action[AnyContent] = Action.async {
		newsDAO.delete(id).map {
			case true => NoContent
			case false => InternalServerError
		}
	}
}
