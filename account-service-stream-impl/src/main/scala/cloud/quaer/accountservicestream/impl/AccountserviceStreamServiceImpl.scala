package cloud.quaer.accountservicestream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import cloud.quaer.accountservicestream.api.AccountserviceStreamService
import cloud.quaer.accountservice.api.AccountserviceService

import scala.concurrent.Future

/**
  * Implementation of the AccountserviceStreamService.
  */
class AccountserviceStreamServiceImpl(accountserviceService: AccountserviceService) extends AccountserviceStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(accountserviceService.hello(_).invoke()))
  }
}
