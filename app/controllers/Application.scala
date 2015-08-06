package controllers

import play.api._
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok("Your application is ready")
  }

  def getRate() = Action {
    Ok("getRate(): Success")
  }
}
