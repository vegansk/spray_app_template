import spray.routing.Directives._
import spray.http.MediaTypes._

package object rest {

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
