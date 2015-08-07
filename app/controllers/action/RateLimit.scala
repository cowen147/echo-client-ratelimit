package controllers.action

import play.api.Play.current
import play.api.libs.ws._
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.{ActionBuilder, Request, WrappedRequest, Result}
import scala.concurrent.{Future}
import controllers.action.RateLimit

class RateLimitRequest[A](request: Request[A]) extends WrappedRequest[A](request)

object RateLimit extends ActionBuilder[RateLimitRequest] {
  def invokeBlock[A](request: Request[A], block: (RateLimitRequest[A]) => Future[Result]): Future[Result] = {
    val holder: WSRequestHolder = WS.url("http://localhost:9000/echo360/api/v1/requests/123456")
    val futureResponse: Future[WSResponse] = holder.post("")

    futureResponse.flatMap {
      response =>
        // Check rate limit
        val body = response.body
        Logger.info(s"************** Check rate limit: $body")

        if (body == "Yes") {
          block(new RateLimitRequest(request))
        } else {
          // Forbidden("Rate limit exceeded")
          // Future.successful(Forbidden("Rate limit exceeded"))
          block(new RateLimitRequest(request))
        }
    }
  }
}
