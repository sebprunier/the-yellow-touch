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
          title = "Macron, Louis XIV, même destin",
          image = "macron-louis-xiv.png",
          tags = Seq("Macron", "Louis XIV", "Guillotine")
        ),
        News(
          title = "Les Gilets Jaunes sur PornHub",
          image = "pornhub.png",
          tags = Seq("Pornhub", "Tu l'as vu mon gilet ?")
        ),
        News(
          title = "Vous ne passerez pas !",
          image = "you-shall-not-pass.png",
          tags = Seq("Blocage", "Dédé", "On lâche rien")
        ),
        News(
          title = "Interview Konbini de Maxou",
          image = "maxime-nicolle-konbini.png",
          tags = Seq("Maxime Nicolle", "Konbini", "Tomber le masque")
        ),
        News(
          title = "Moi aussi je peux mettre un gilet",
          image = "macron.png",
          tags = Seq("Macron", "Je vous ai compris")
        ),
        News(
          title = "Le Jaune contre le Vert",
          image = "ecolos-bobos.png",
          tags = Seq("Ecolos", "Bobos", "Complots")
        ),
        News(
          title = "Pose les deux pieds en canard ♪",
          image = "chenille.png",
          tags = Seq("Chenille", "Rond-Point", "On lâche rien")
        ),
        News(
          title = "Dédé, daltonien, soutient la cause",
          image = "daltonien.png",
          tags = Seq("Dédé", "CGT")
        )
      )
    )
  }

}