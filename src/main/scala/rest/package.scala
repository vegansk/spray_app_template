import spray.routing.Directives._
import spray.http.MediaTypes._
import argonaut._, Argonaut._
import utils.ArgonautSupport._
import models._, Account._

package object rest {

  import scala.concurrent.{ ExecutionContext, Future }


  def getAccount(id: Int)(implicit ec: ExecutionContext) =
    complete {
      Future(db.getAccount(id).asJson)
    }

  def index = respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
    complete {
      <html>
        <body>
        <h1>Say hello to <i>spray-routing</i> on <i>spray-can</i>!</h1>
        </body>
        </html>
    }
  }

}
