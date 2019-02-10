package models

import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

case class News
(
  title: String,
  image: String,
  tags: Seq[String]
)

@Singleton
class NewsService @Inject()() {

  def all(): Future[Seq[News]] = {
    Future.successful(
      Seq(
        News(
          title = "Maxime Nicolle quitte la France",
          image = "maxime-nicolle-quitte-france.png",
          tags = Seq("Maxime Nicolle", "Asile Politique", "Corée du Nord")
        ),
        News(
          title = "Maxime Nicolle quitte la France",
          image = "maxime-nicolle-quitte-france.png",
          tags = Seq("Maxime Nicolle", "Asile Politique", "Corée du Nord")
        ),
        News(
          title = "Maxime Nicolle quitte la France",
          image = "maxime-nicolle-quitte-france.png",
          tags = Seq("Maxime Nicolle", "Asile Politique", "Corée du Nord")
        ),
        News(
          title = "Maxime Nicolle quitte la France",
          image = "maxime-nicolle-quitte-france.png",
          tags = Seq("Maxime Nicolle", "Asile Politique", "Corée du Nord")
        ),
        News(
          title = "Maxime Nicolle quitte la France",
          image = "maxime-nicolle-quitte-france.png",
          tags = Seq("Maxime Nicolle", "Asile Politique", "Corée du Nord")
        ),
        News(
          title = "Maxime Nicolle quitte la France",
          image = "maxime-nicolle-quitte-france.png",
          tags = Seq("Maxime Nicolle", "Asile Politique", "Corée du Nord")
        ),
        News(
          title = "Maxime Nicolle quitte la France",
          image = "maxime-nicolle-quitte-france.png",
          tags = Seq("Maxime Nicolle", "Asile Politique", "Corée du Nord")
        ),
        News(
          title = "Maxime Nicolle quitte la France",
          image = "maxime-nicolle-quitte-france.png",
          tags = Seq("Maxime Nicolle", "Asile Politique", "Corée du Nord")
        )
      )
    )
  }

}