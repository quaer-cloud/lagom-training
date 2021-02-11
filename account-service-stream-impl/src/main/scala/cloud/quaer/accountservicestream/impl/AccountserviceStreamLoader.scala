package cloud.quaer.accountservicestream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import cloud.quaer.accountservicestream.api.AccountserviceStreamService
import cloud.quaer.accountservice.api.AccountserviceService
import com.softwaremill.macwire._

class AccountserviceStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new AccountserviceStreamApplication(context) {
      override def serviceLocator: NoServiceLocator.type = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new AccountserviceStreamApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[AccountserviceStreamService])
}

abstract class AccountserviceStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer: LagomServer = serverFor[AccountserviceStreamService](wire[AccountserviceStreamServiceImpl])

  // Bind the AccountserviceService client
  lazy val accountserviceService: AccountserviceService = serviceClient.implement[AccountserviceService]
}
