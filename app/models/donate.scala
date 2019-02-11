package models

import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

case class Donate
(
  enable: Boolean,
  baseline: String,
  defaultAmount: Int,
  actionColor: String,
  actionLabel: String,
  actionLabelColor: String
)

@Singleton
class DonateService @Inject()() {

  def get(): Future[Donate] = {
    Future.successful(
      Donate(
        enable = true,
        baseline = "Soutenez la cause en participant à notre cagnotte en ligne !",
        defaultAmount = 10,
        actionColor = "#ffef00",
        actionLabel = "Donner !",
        actionLabelColor = "black"
      )
    )
  }

}