package controllers

import play.api.mvc.InjectedController

class Healthcheck extends InjectedController {

	def status() = Action {
		Ok("OK")
	}
}
