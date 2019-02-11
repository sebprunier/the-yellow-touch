package models

import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

case class March
(
  when: String,
  where: String
)

@Singleton
class MarchService @Inject()() {

  def next(): Future[March] = {
    Future.successful(
      March(
        when = "Samedi 16 février",
        where = "Poitiers"
      )
    )
  }

}