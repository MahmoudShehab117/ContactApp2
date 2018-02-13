//import BackEnd.DatabaseSchema.Contacts
import BackEnd.DatabaseSchema.databaseEntities.db
import BackEnd.DatabaseSchema.{Contacts, databaseEntities}
import BackEnd.entity.{Contact, Phone}
import akka.actor._
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import slick.jdbc.H2Profile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import BackEnd.services._

import scala.concurrent.Future



object Main extends App with RestInterface {
  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")

  implicit val system = ActorSystem("contact-service")
  implicit val materializer = ActorMaterializer()

  implicit val executionContext = system.dispatcher


  val api = routes

  databaseEntities.recreateSchema()

  Http().bindAndHandle(handler = api, interface = host, port = port) map { binding =>
    println(s"REST interface bound to ${binding.localAddress}") } recover { case ex =>
    println(s"REST interface could not bind to $host:$port", ex.getMessage)
  }

}
