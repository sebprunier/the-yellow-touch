package controllers

import javax.inject._
import models._
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
    val donateFuture: Future[Donate] = donateService.getCheckingLanguageEnabled(language)
    val allNewsFuture: Future[Seq[News]] = newsService.allCheckingCompromising()

    for {
      nextMarch <- nextMarchFuture
      donate <- donateFuture
      allNews <- allNewsFuture
    } yield Ok(views.html.index(nextMarch, donate, allNews))

  }
}
