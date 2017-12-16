package BackEnd.services

import BackEnd.entity.{Contact, ContactUpdate}

import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source

class ContactService(implicit  val executionContext : ExecutionContext) {

  var contacts=Vector.empty[Contact];

  def createContact(contact:Contact):Future[Option[BigInt]]=Future{

    contacts.find(_.id==contact.id) match {
      case Some(q)=> None
      case None => contacts = contacts :+ contact
        Some(contact.id)

    }
  }

  def getContact(id :BigInt):Future[Option[Contact]] = Future{
    contacts.find(_.id==id)
  }

  def getAllContacts():Future[Option[Vector[Contact]]] = Future{Some(contacts)}

  def updateContact(id:BigInt, update:ContactUpdate):Future[Option[Contact]]={

    def updateEntity(contact :Contact):Contact={
      val fName = update.firstName.getOrElse(contact.firstName)
      val lName = update.lastName.getOrElse(contact.lastName)
      val email = update.email.getOrElse(contact.email)
      val phone = update.phone.getOrElse(contact.phone)
      Contact(id,fName,lName,email,phone)
    }
    getContact(id).flatMap { mayBeContact =>
      mayBeContact match {
        case None => Future{None}
        case Some(contact)=> val updatedContact = updateEntity(contact)
          deletedContact(id).flatMap {_ => createContact(updatedContact).map(_ => Some(updatedContact))}
  }
}}

  def deletedContact(id:BigInt):Future[Unit]=Future{
    contacts = contacts.filterNot(_.id==id)
  }
}
