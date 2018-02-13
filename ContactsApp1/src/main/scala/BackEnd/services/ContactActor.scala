package BackEnd.services


import BackEnd.entity.{Contact, ContactDetails, Phone}
import akka.actor.{Actor, ActorLogging}
import scala.concurrent.Future


case class CreateContact(contact:Contact)
case class GetAllContact()
case class UpdateContact(id:Int, update:Contact)
case class DeleteContact(id:Int)
case class GetContactDetails(id:Int)
case class CreateContactDetails(contactDetails: ContactDetails)
case class UpdateContactDetails(id:Int,update:ContactDetails)


class ContactActor extends Actor with ActorLogging {

  import context.dispatcher

  val contactService: ContactService = new ContactService()

  override def receive: Receive = {

        case CreateContactDetails(contact: ContactDetails) =>
          //super one from yehia
          val newContact: Future[ContactDetails] = contactService.createContactDetails(contact)
          log.info(s"got request in create contact, payload is $newContact")
          sender ! newContact
        case GetAllContact() => sender() ! contactService.getAllContacts()
        case DeleteContact(id:Int) => sender() ! contactService.deleteContact(id)
        case UpdateContact(id :Int ,contactUpdated: Contact) => sender() ! contactService.updateContact(id,contactUpdated)
        case GetContactDetails(id:Int) => sender() ! contactService.getContactDetails(id)
        case UpdateContactDetails(id :Int ,contactUpdated: ContactDetails) =>
          sender() ! contactService.updateContactDetails(id,contactUpdated)

  }
}


