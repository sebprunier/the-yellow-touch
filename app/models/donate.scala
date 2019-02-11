package models

import java.util.UUID

import config.CustomConfiguration
import izanami.Variant
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

case class Donate
(
  variant: String,
  clientId: String,
  enable: Boolean,
  baseline: String,
  defaultAmount: Int,
  actionColor: String,
  actionLabel: String,
  actionLabelColor: String
)

object Donate {

  def default(enabled: Boolean) = Donate(
    variant = "-",
    clientId = UUID.randomUUID().toString,
    enable = enabled,
    baseline = "Soutenez la cause en participant à notre cagnotte en ligne !",
    defaultAmount = 10,
    actionColor = "#ffef00",
    actionLabel = "Donner !",
    actionLabelColor = "black"
  )

  def variantA(enabled: Boolean, clientId: String) = Donate(
    variant = "A",
    clientId = clientId,
    enable = enabled,
    baseline = "Soutenez la cause en participant à notre cagnotte en ligne !",
    defaultAmount = 10,
    actionColor = "#ffef00",
    actionLabel = "Donner !",
    actionLabelColor = "black"
  )

  def variantB(enabled: Boolean, clientId: String) = Donate(
    variant = "B",
    clientId = clientId,
    enable = enabled,
    baseline = "Merci de faire un don d'au moins 50 euros sur notre cagnotte. Sinon attention à vous :-)",
    defaultAmount = 50,
    actionColor = "red",
    actionLabel = "Je contribue",
    actionLabelColor = "white"
  )
}

@Singleton
class DonateService @Inject()()(implicit configuration: CustomConfiguration, ec: ExecutionContext) {

  def get(): Future[Donate] = {
    Future.successful(
      Donate.default(true)
    )
  }

  def getCheckingEnabled(): Future[Donate] = {
    configuration.featureClient.checkFeature("the-yellow-touch:donate:enabled").map(donateEnabled => {
      Donate.default(donateEnabled)
    })
  }

  def getCheckingLanguageEnabled(language: String): Future[Donate] = {
    val context = Json.obj(
      "language" -> language
    )
    configuration.featureClient.checkFeature("the-yellow-touch:donate:language-enabled", context).map(donateEnabled => {
      Donate.default(donateEnabled)
    })
  }

  def getFromExperiment(): Future[Donate] = {
    val clientId = UUID.randomUUID().toString
    configuration.experimentsClient.getVariantFor("the-yellow-touch:donate:ui-experiment", clientId).map {
      case (Some(Variant("A", _, _))) => experimentA(clientId)
      case (Some(Variant("B", _, _))) => experimentB(clientId)
      case (_) => experimentA(clientId)
    }
  }

  private def experimentA(clientId: String) = {
    configuration.experimentsClient.markVariantDisplayed("the-yellow-touch:donate:ui-experiment", clientId)
    Donate.variantA(true, clientId)
  }

  private def experimentB(clientId: String) = {
    configuration.experimentsClient.markVariantDisplayed("the-yellow-touch:donate:ui-experiment", clientId)
    Donate.variantB(true, clientId)
  }

  def winExperiment(clientId: String) = {
    configuration.experimentsClient.markVariantWon("the-yellow-touch:donate:ui-experiment", clientId)
  }
}