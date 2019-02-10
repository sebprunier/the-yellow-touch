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
        baseline = "Soutenez la cause !",
        defaultAmount = 10,
        actionColor = "yellow",
        actionLabel = "Donner",
        actionLabelColor = "black"
      )
    )
  }

}