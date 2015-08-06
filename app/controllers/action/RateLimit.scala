package controllers.action

import play.api._
import play.api.Play.current
import play.api.libs.ws._
import play.api.libs.ws.ning.NingAsyncHttpClientConfigBuilder
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.concurrent.Execution
import play.api.mvc.{ActionBuilder, Request, Result}
import scala.concurrent.{ExecutionContext, Future}
import controllers.action.RateLimit

object RateLimit extends ActionBuilder[Request] {
  def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    Logger.info("************** Check rate limit...")

    val holder: WSRequestHolder = WS.url("http://localhost:9000/echo360/api/v1/requests/123456")
    val futureResponse: Future[WSResponse] = holder.post("")

    futureResponse.map {
      response =>
        Logger.info("************")
        block(request)
    }
  }
}
