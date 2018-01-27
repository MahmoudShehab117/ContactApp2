package BackEnd.services

import BackEnd.entity.{Contact, ContactUpdate}
import akka.Done
import akka.actor.Actor

import scala.concurrent.ExecutionContext


case class CreateContact(contact:Contact)
case class GetContact(id:Int)
case class GetAllContact()
case class UpdateContact(id:Int, update:ContactUpdate)
case class DeleteContact(id:Int)


class ContactActor extends Actor {

  import context.dispatcher

  val contactService: ContactService = new ContactService()

  override def receive: Receive = {

   case CreateContact(contact:Contact) =>
     sender() ! contactService.createContact(contact)

   case GetContact(id:Int) => sender() ! contactService.getContact(id)


   case GetAllContact() => sender() ! contactService.getAllContacts()


   case DeleteContact(id:Int) => contactService.deleteContact(id)
       sender() ! Done


   case UpdateContact(id :Int ,contactUpdated: ContactUpdate) =>
     contactService.updateContact(id,contactUpdated)
     sender() ! Done



  }

}


