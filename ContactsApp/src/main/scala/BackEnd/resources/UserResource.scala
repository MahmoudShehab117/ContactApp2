package BackEnd.resources
import akka.http.scaladsl.server.Route
import BackEnd.entity.{Contact, ContactUpdate, Users}
import BackEnd.routing.MyResource
import BackEnd.services.{ContactService, UserService}
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._

import scala.collection.immutable
trait UserResource extends MyResource{

  val userService :UserService
  val userSettings = CorsSettings.defaultSettings.copy(allowedMethods = immutable.Seq(GET, POST))

  def UserRoutes: Route = cors(userSettings){pathPrefix("users") {
    pathEnd {
      post {
        entity(as[Users]) { user =>
          completeWithLocationHeader(
            resourceId = userService.compareUser(user),
            ifDefinedStatus = 200, ifEmptyStatus = 401)
        }
      }}
  }}

}
