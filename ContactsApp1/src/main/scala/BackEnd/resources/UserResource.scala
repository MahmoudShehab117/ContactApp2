package BackEnd.resources
import akka.http.scaladsl.server.Route
import BackEnd.entity.{Contact, ContactUpdate, Users}
import BackEnd.routing.MyResource
import akka.pattern.ask
import BackEnd.services._
import akka.actor.{ActorRef, ActorSystem, Props}
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.util.Timeout

import scala.concurrent.duration._
import scala.collection.immutable
import scala.concurrent.Future
trait UserResource extends MyResource{

  val  userActorInstance : ActorRef = ActorSystem("LoginSystem").actorOf(Props[UserActor],"userActorInstance")
  val userService :UserService
  val userSettings = CorsSettings.defaultSettings.copy(allowedMethods = immutable.Seq(GET, POST))
  implicit val timeout: Timeout = Timeout(10.seconds)
  def UserRoutes: Route = cors(userSettings){pathPrefix("users") {
    pathEnd {
      post {
        entity(as[Users]) { user =>
          onSuccess(userActorInstance ? CompareUser(user))
          {
            case t: Future[Option[String]] => complete(StatusCodes.OK)
            case _ => complete(StatusCodes.Unauthorized)
          }

        }
      }}
  }}
}
