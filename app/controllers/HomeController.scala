package controllers

import javax.inject._
import models._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject()
(
  marchService: MarchService,
  donateService: DonateService,
  newsService: NewsService,
  cc: ControllerComponents
)(
  implicit executionContext: ExecutionContext
) extends AbstractController(cc) {

  def index(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val language = request.headers.get("Accept-Language").getOrElse("--")
    println(s"Language is: $language")

    val nextMarchFuture: Future[March] = marchService.nextFromConfiguration()
    val donateFuture: Future[Donate] = donateService.getFromExperiment()
    val allNewsFuture: Future[Seq[News]] = newsService.allCheckingCompromising()

    for {
      nextMarch <- nextMarchFuture
      donate <- donateFuture
      allNews <- allNewsFuture
    } yield Ok(views.html.index(nextMarch, donate, allNews))

  }

  def winExperiment(clientId: String) = Action.async { implicit request: Request[AnyContent] =>
    donateService.winExperiment(clientId).map(experimentVariantWon => {
      Ok(Json.obj(
        "experimentId" -> experimentVariantWon.experimentId,
        "clientId" -> experimentVariantWon.clientId,
        "variantId" -> experimentVariantWon.variantId
      ))
    })
  }
}
