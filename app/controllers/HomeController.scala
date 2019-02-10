package controllers

import javax.inject._
import models.{March, MarchService}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject()
(
  marchService: MarchService,
  cc: ControllerComponents
)(
  implicit executionContext: ExecutionContext
) extends AbstractController(cc) {

  def index(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val nextMarchFuture: Future[March] = marchService.next()

    for {
      nextMarch <- nextMarchFuture
    } yield Ok(views.html.index(nextMarch))

  }
}
