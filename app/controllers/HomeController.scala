package controllers

import javax.inject._
import models.{Donate, DonateService, March, MarchService}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject()
(
  marchService: MarchService,
  donateService: DonateService,
  cc: ControllerComponents
)(
  implicit executionContext: ExecutionContext
) extends AbstractController(cc) {

  def index(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val nextMarchFuture: Future[March] = marchService.next()
    val donateFuture: Future[Donate] = donateService.get()

    for {
      nextMarch <- nextMarchFuture
      donate <- donateFuture
    } yield Ok(views.html.index(nextMarch, donate))

  }
}
