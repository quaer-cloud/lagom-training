package cloud.quaer.accountservicestream.api

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.lightbend.lagom.scaladsl.api.CircuitBreaker.PerService
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

/**
  * The account-service stream interface.
  *
  * This describes everything that Lagom needs to know about how to serve and
  * consume the AccountserviceStream service.
  */
trait AccountserviceStreamService extends Service {

  def stream: ServiceCall[Source[String, NotUsed], Source[String, NotUsed]]

  override final def descriptor: Descriptor = {
    import Service._

    named("account-service-stream")
      .withCalls(
        namedCall("stream", stream)
      ).withAutoAcl(true)
      .withCircuitBreaker(PerService)
  }
}

