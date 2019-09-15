package BackEnd.resources

import akka.http.scaladsl.server.Route
import BackEnd.entity.{Contact, ContactDetails, Phone, Report}
import BackEnd.routing.MyResource
import BackEnd.services._
import akka.pattern.ask

import scala.concurrent.duration._
import akka.actor.{ActorRef, ActorSystem, Props}
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.util.Timeout
import slick.lifted.Rep

import scala.collection._
import scala.concurrent.Future

trait ContactResource extends MyResource {

  val contactActorInstance: ActorRef = ActorSystem("ContactSystem").actorOf(Props[ContactActor], "contactActorInstance")
  val reportActorInstance: ActorRef = ActorSystem("ReportSystem").actorOf(Props[ReportActor], "reportActorInstance")

  val contactService: ContactService
  val settings = CorsSettings.defaultSettings.copy(allowedMethods =
    immutable.Seq(GET, POST, HEAD, OPTIONS, HttpMethods.PUT, HttpMethods.DELETE))

  def ContactRoutes: Route = cors(settings) {

    implicit val timeout: Timeout = Timeout(10 seconds)

    pathPrefix("contacts") {
      pathEnd {
        post {
          entity(as[ContactDetails]) {
            contact =>
              val future: Future[Any] = contactActorInstance ? CreateContactDetails(contact)
              onSuccess(future) {
                case t: Future[ContactDetails] => completeWithLocationHeader(t, 201, 400)
                case _ => complete(StatusCodes.BadRequest)
                //complete(StatusCodes.Created)
              }
          }
        } ~
        get {
            onSuccess(contactActorInstance ? GetAllContact()) {
              case t: Future[Seq[Contact]] => complete(t)
              case _ => complete(StatusCodes.NotFound)
            }
            // complete (contactService.getAllContacts())
          }
      } ~
        path(Segment) { id =>
          get {
            onSuccess(contactActorInstance ? GetContactDetails(id.toInt)) {
              case t: Future[Option[ContactDetails]] => complete(t)
              case _ => complete(StatusCodes.NotFound)
            }
          } ~
            put {
              entity(as[ContactDetails]) { update =>
                onSuccess(contactActorInstance ? UpdateContactDetails(id.toInt, update)) {
                  case t: Future[Contact] =>
                    println("t")
                    complete(t)
                  case _ =>
                    println("_")
                    complete(StatusCodes.NotFound)
                }
              }
            } ~
            delete {
              onSuccess(contactActorInstance ? DeleteContact(id.toInt)) {
                case t: Future[Int] => complete(t)
                case _ => complete(StatusCodes.NotFound)
              }

              //complete(contactService.deleteContact(id.toInt))
            }
        }
    } ~
    pathPrefix("reports"){
        get {
          onSuccess(reportActorInstance ? GetReport()) {
            case t : Future[Report] => complete(t)
            case _ => complete(StatusCodes.NotFound)
          }
        }
      }
  }
}

