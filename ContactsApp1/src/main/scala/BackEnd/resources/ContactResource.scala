package BackEnd.resources

import akka.http.scaladsl.server.Route
import BackEnd.entity.Contact
import BackEnd.routing.MyResource
import BackEnd.entity.ContactUpdate
import BackEnd.services.ContactService
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._

import scala.collection._

trait ContactResource extends MyResource {

  val contactService: ContactService
  val settings = CorsSettings.defaultSettings.copy(allowedMethods =
    immutable.Seq(GET, POST, HEAD, OPTIONS,HttpMethods.PUT,HttpMethods.DELETE))

  def ContactRoutes: Route = cors(settings){pathPrefix("contacts") {
    pathEnd {
      post {
        entity(as[Contact]) { contact =>
          completeWithLocationHeader(
            resourceId = contactService.createContact(contact),
            ifDefinedStatus = 201, ifEmptyStatus = 409)
        }
      } ~
      get {

        complete (contactService.getAllContacts())
      }
    } ~
    path(Segment) { id =>
      get {
        complete( contactService.getContact(id.toInt))

      } ~
      put {
        entity(as[ContactUpdate]) { update =>
          complete(contactService.updateContact(id.toInt, update))
        }
      } ~
      delete {
        complete(contactService.deletedContact(id.toInt))
      }
    }

  }}
}

