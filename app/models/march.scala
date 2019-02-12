package models

import config.CustomConfiguration
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}

case class March
(
  when: String,
  where: String
)

@Singleton
class MarchService @Inject()()(implicit configuration: CustomConfiguration, ec: ExecutionContext) {

  // Next march
  def next(): Future[March] = {
    Future.successful(
      March(
        when = "Samedi 9 février",
        where = "Valence"
      )
    )
  }

  // Get next march from Izanami configuration
  def nextFromConfiguration(): Future[March] = {
    configuration.configClient.config("the-yellow-touch:next-march").map(config => {
      March(
        when = (config \ "when").as[String],
        where = (config \ "where").as[String]
      )
    })
  }

}