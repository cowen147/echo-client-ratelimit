package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current
import play.api.libs.ws._
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future
import controllers.action.RateLimit

class Application extends Controller {

  def index = Action {
    Ok("Your application is ready")
  }

  def makeAPICall1() = RateLimit.async {
    val holder: WSRequestHolder = WS.url("http://localhost:9000/echo360/api/v1/requests/123456")
    val futureResponse: Future[WSResponse] = holder.post("")

    futureResponse.map {
      response =>
        Logger.info(response.body)
        val body = response.body
        Ok(s"Success: makeAPICall1(): $body")
    }
  }

  def makeAPICall2() = RateLimit.async {
    val futureResponse = scala.concurrent.Future { "future response" }

    futureResponse.map {
      response =>
        Ok(s"Success: makeAPICall2(): $response")
    }
  }

  def makeAPICall3() = Action.async {
     Future.successful(Forbidden("Rate limit exceeded"))
  }
}
