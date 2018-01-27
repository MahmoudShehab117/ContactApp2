package BackEnd.services

import BackEnd.entity.Users
import akka.actor.Actor
import akka.util.Timeout
import scala.concurrent.duration._


case class CompareUser(user : Users)

class UserActor extends Actor{
  import context.dispatcher

  val userService: UserService = new UserService()

  implicit val timeout: Timeout = Timeout(10.seconds)
  override def receive: Receive = {

    case CompareUser(user:Users) => sender() ! userService.compareUser(user)

  }

}
