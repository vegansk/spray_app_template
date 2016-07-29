import spray.routing.Directives._
import rest._

package object routes {

  import akka.actor.ActorRefFactory
  import spray.routing.directives.ContentTypeResolver
  import spray.util.LoggingContext


  def routes(implicit resolver: ContentTypeResolver, refFactory: ActorRefFactory, log: LoggingContext) =
    path("old") {
      get {
        index
      }
    } ~
    pathPrefix("") {
      getFromResourceDirectory("static_content")
    }
}
