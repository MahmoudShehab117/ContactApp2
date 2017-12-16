
import BackEnd.entity.Contact
import akka.actor._
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import org.json4s.native.Json

import scala.concurrent.duration._
import scala.io.Source
import scala.util.parsing.json._

object Main extends App with RestInterface {
  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")

  implicit val system = ActorSystem("contact-service")
  implicit val materializer = ActorMaterializer()


  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  val api = routes

  Http().bindAndHandle(handler = api, interface = host, port = port) map { binding =>
    println(s"REST interface bound to ${binding.localAddress}") } recover { case ex =>
    println(s"REST interface could not bind to $host:$port", ex.getMessage)
  }

}
