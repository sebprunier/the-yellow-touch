package models

import config.CustomConfiguration
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

case class Donate
(
  enable: Boolean,
  baseline: String,
  defaultAmount: Int,
  actionColor: String,
  actionLabel: String,
  actionLabelColor: String
)

object Donate {
  def variantA(enabled: Boolean) = Donate(
    enable = enabled,
    baseline = "Soutenez la cause en participant à notre cagnotte en ligne !",
    defaultAmount = 10,
    actionColor = "#ffef00",
    actionLabel = "Donner !",
    actionLabelColor = "black"
  )
}

@Singleton
class DonateService @Inject()()(implicit configuration: CustomConfiguration, ec: ExecutionContext) {

  def get(): Future[Donate] = {
    Future.successful(
      Donate.variantA(true)
    )
  }

  def getCheckingEnabled(): Future[Donate] = {
    configuration.featureClient.checkFeature("the-yellow-touch:donate:enabled").map(donateEnabled => {
      Donate.variantA(donateEnabled)
    })
  }

  def getCheckingLanguageEnabled(language: String): Future[Donate] = {
    val context = Json.obj(
      "language" -> language
    )
    configuration.featureClient.checkFeature("the-yellow-touch:donate:language-enabled", context).map(donateEnabled => {
      Donate.variantA(donateEnabled)
    })
  }
}