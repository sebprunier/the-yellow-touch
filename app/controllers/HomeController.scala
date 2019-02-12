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
    // Get language from request
    val language = request.headers.get("Accept-Language").getOrElse("--")
    println(s"Language is: $language")

    // Get data from services
    val nextMarchFuture: Future[March]   = marchService.next()
    val donateFuture: Future[Donate]     = donateService.get()
    val allNewsFuture: Future[Seq[News]] = newsService.all()

    // Render view
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
