package BackEnd.services

import BackEnd.entity.{Contact, ContactDetails, Phone}
import akka.actor.{Actor, ActorLogging}
import scala.concurrent.Future



case class GetReport()
class ReportActor extends Actor with ActorLogging {

  import context.dispatcher

  val reportService: ReportService = new ReportService()

  override def receive: Receive = {
    case GetReport() =>
      sender() ! reportService.getReport()

  }

}
