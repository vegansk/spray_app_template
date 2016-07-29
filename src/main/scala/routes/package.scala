import spray.routing.Directives._
import rest._

package object routes {

  import akka.actor.ActorRefFactory
  import scala.concurrent.ExecutionContext
  import spray.routing.directives.ContentTypeResolver
  import spray.util.LoggingContext

  def restRoutes(implicit ec: ExecutionContext) =
    pathPrefix("accounts" / IntNumber) { id => 
      get {
        getAccount(id)
      }
    }

  def routes(implicit resolver: ContentTypeResolver, refFactory: ActorRefFactory, log: LoggingContext, ec: ExecutionContext) =
    restRoutes ~
    path("") {
      getFromResource("static_content/index.html")
    } ~
    pathPrefix("") {
      getFromResourceDirectory("static_content")
    }
}
