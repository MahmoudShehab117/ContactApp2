package BackEnd.resources

import akka.http.scaladsl.server.Route
import BackEnd.entity.Contact
import BackEnd.routing.MyResource
import BackEnd.entity.ContactUpdate
import BackEnd.services._
import akka.Done
import akka.pattern.ask
import scala.concurrent.duration._
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.util.Timeout
import scala.collection._
import scala.concurrent.Future

trait ContactResource extends MyResource {

  val  contactActorInstance : ActorRef = ActorSystem("ContactSystem").actorOf(Props[ContactActor],"contactActorInstance")

  val contactService: ContactService
  val settings = CorsSettings.defaultSettings.copy(allowedMethods =
    immutable.Seq(GET, POST, HEAD, OPTIONS,HttpMethods.PUT,HttpMethods.DELETE))



  def ContactRoutes: Route = cors(settings) {

    implicit val timeout: Timeout = Timeout(10 seconds)
    pathPrefix("contacts") {
    pathEnd {
      post {
        entity(as[Contact]) {
          contact =>
            onSuccess(contactActorInstance ? CreateContact(contact)) {
              case t : Future[Option[Contact]] =>
                completeWithLocationHeader(resourceId = t,ifDefinedStatus = 201, ifEmptyStatus = 409)
              case _ => complete(StatusCodes.Conflict)
              //complete(StatusCodes.Created)
          }
        }
      } ~
      get {

        onSuccess(contactActorInstance ? GetAllContact()) {
          case t : Future[Option[Vector[Contact]]] => complete(t)
          case _ => complete(StatusCodes.NotFound)
        }
        // complete (contactService.getAllContacts())
      }
    } ~
    path(Segment) { id =>
      get {
        onSuccess(contactActorInstance ? GetContact(id.toInt) ) {
          case t : Future[Option[Contact]] => complete(t)
          case _ => complete(StatusCodes.NotFound)
        }
      } ~
      put {
        entity(as[ContactUpdate]) { update =>
          onSuccess(contactActorInstance ? UpdateContact(id.toInt,update) ) {
            case t : Future[Option[Contact]] => complete(t)
            case _ => complete(StatusCodes.NotFound)
          }
          //complete(contactService.updateContact(id.toInt, update))
        }
      } ~
      delete {
        onSuccess(contactActorInstance ? DeleteContact(id.toInt) ) {
          case t : Future[Option[Contact]] => complete(t)
          case _ => complete(StatusCodes.NotFound)
        }

        //complete(contactService.deleteContact(id.toInt))
      }
    }

  }

  }
}

