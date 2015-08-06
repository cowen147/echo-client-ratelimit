package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current
import play.api.libs.ws._
import play.api.libs.ws.ning.NingAsyncHttpClientConfigBuilder
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future

class Application extends Controller {

  def index = Action {
    Ok("Your application is ready")
  }

  def getRate() = Action.async {
    val holder: WSRequestHolder = WS.url("http://localhost:9000/echo360/api/v1/requests/123456")
    val futureResponse: Future[WSResponse] = holder.post("")

    futureResponse.map {
      response =>
        Logger.debug(response.body)
        val body = response.body
        Ok(s"Success: getRate(): $body")
    }
  }
}
