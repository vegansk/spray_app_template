import org.specs2.mutable.Specification
import spray.routing.HttpService
import spray.testkit.Specs2RouteTest
import spray.http._
import StatusCodes._
import routes._

class MyServiceSpec extends Specification with Specs2RouteTest with HttpService {
  def actorRefFactory = system

  "Service" should {

    "return a greeting for GET requests to the root path" in {
      Get() ~> routes ~> check {
        responseAs[String] must contain("Say hello")
      }
    }

    "leave GET requests to other paths unhandled" in {
      Get("/kermit") ~> routes ~> check {
        handled must beFalse
      }
    }

    "return a MethodNotAllowed error for PUT requests to the root path" in {
      Put() ~> sealRoute(routes) ~> check {
        status === MethodNotAllowed
        responseAs[String] === "HTTP method not allowed, supported methods: GET"
      }
    }
  }
}
