package BackEnd.services

import BackEnd.entity.Users

import scala.concurrent.{ExecutionContext, Future}

class UserService(implicit  val executionContext : ExecutionContext) {

  var users=Vector.empty[Users]
  users = users :+ Users("admin","password")
  users = users :+ Users("test","test")

  def createUser(user :Users ):Future[Option[String]]=Future{

    users.find(_.userName == user.userName)match{
      case Some(u)=> None
      case None => users = users :+ user
      Some(user.userName)
    }
  }

  def compareUser(user : Users):Future[Option[String]]=Future{

    users.find(_.userName == user.userName) match{
      case None => None
      case Some(u)=> Some(u.userName)
    }

  }

}
