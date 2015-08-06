package controllers.action

import play.api.libs.concurrent.Execution
import play.api.mvc.{ActionBuilder, Request, Result}
import play.api.Logger
import scala.concurrent.{ExecutionContext, Future}

object RateLimit extends ActionBuilder[Request] {
  def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    Logger.info("************** Check rate limit...")
    block(request)
  }
}
