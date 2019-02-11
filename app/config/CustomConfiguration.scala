package config

import java.time.ZoneId

import akka.actor.ActorSystem
import com.typesafe.config.Config
import izanami.scaladsl.{ConfigClient, ExperimentsClient, FeatureClient, IzanamiClient}
import izanami.{ClientConfig, Strategies}
import javax.inject.{Inject, Singleton}
import play.api.{ConfigLoader, Configuration, Environment}

case class IzanamiConfiguration
(
  host: String,
  clientId: String,
  clientSecret: String,
  zoneId: String
)

object IzanamiConfiguration {
  implicit val configLoader: ConfigLoader[IzanamiConfiguration] = new ConfigLoader[IzanamiConfiguration] {
    override def load(rootConfig: Config, path: String): IzanamiConfiguration = {
      val config = rootConfig.getConfig(path)
      IzanamiConfiguration(
        host = config.getString("host"),
        clientId = config.getString("clientId"),
        clientSecret = config.getString("clientSecret"),
        zoneId = config.getString("zoneId")
      )
    }
  }
}

@Singleton
class CustomConfiguration @Inject()(configuration: Configuration, environment: Environment)(implicit system: ActorSystem) {

  val izanamiConfiguration: IzanamiConfiguration = configuration.get[IzanamiConfiguration]("izanami")

  lazy val izanamiClient: IzanamiClient = IzanamiClient(
    ClientConfig(
      host = izanamiConfiguration.host,
      clientId = Some(izanamiConfiguration.clientId),
      clientSecret = Some(izanamiConfiguration.clientSecret),
      zoneId = ZoneId.of(izanamiConfiguration.zoneId)
    )
  )

  lazy val experimentsClient: ExperimentsClient = izanamiClient.experimentClient(
    strategy = Strategies.fetchStrategy()
  )

  lazy val featureClient: FeatureClient = izanamiClient.featureClient(
    strategy = Strategies.fetchStrategy()
  )

  lazy val configClient: ConfigClient = izanamiClient.configClient(
    strategy = Strategies.fetchStrategy()
  )

}