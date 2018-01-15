package BackEnd.services

import BackEnd.entity.Users
import io.getquill.{CamelCase, H2JdbcContext}

import scala.concurrent.{ExecutionContext, Future}

class UserService(implicit  val executionContext : ExecutionContext) {

  var users=Vector.empty[Users]
  users = users :+ Users("admin","password")
  users = users :+ Users("test","test")


  lazy val ctx = new H2JdbcContext(CamelCase, "DataBase")
  import ctx._


// for registration --SignUp

   def createUser(user :Users ):Future[Option[String]]=Future{

    val findUser = ctx.run( quote {query[Users].filter(_.userName == lift(user.userName))}).headOption
    findUser match {
      case Some(_)=> None
      case None => ctx.run(quote {query[Users].insert(lift(user))})
        Some(user.userName)
    }
  }


  //check for userName
  def compareUser(user : Users):Future[Option[String]]=Future{

    val findUser = ctx.run( quote {query[Users].filter(user1 => user1.userName == lift(user.userName) && user1.password == lift(user.password))}).headOption
    findUser match{
      case None => None
      case Some(u)=> Some(user.userName)
    }




  }

}
