import scala.concurrent.ExecutionContext
import akka.http.scaladsl.server.Route
import BackEnd.resources.{ContactResource, UserResource}
import BackEnd.services.{ContactService, UserService}

trait RestInterface extends Resources {

  implicit def executionContext: ExecutionContext

  lazy val contactService = new ContactService()

  lazy val userService = new UserService()

  val routes: Route = ContactRoutes ~ UserRoutes

}

trait Resources extends ContactResource with UserResource

