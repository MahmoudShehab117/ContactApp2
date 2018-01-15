package BackEnd.services

import BackEnd.entity.{Contact, ContactUpdate}
import io.getquill._
import scala.concurrent.{ExecutionContext, Future}
//import scala.io.Source



class ContactService(implicit  val executionContext : ExecutionContext) {

  lazy val ctx = new H2JdbcContext(CamelCase, "DataBase")
  import ctx._

  def createContact(contact:Contact):Future[Option[Contact]]=Future{

    val findContact = ctx.run( quote {query[Contact].filter(_.id == lift(contact.id))}).headOption
      findContact match {
      case Some(_)=> None
      case None => ctx.run(quote {query[Contact].insert(lift(contact))})
        Some(contact)
    }
  }

  def getContact(id :Int):Future[Option[Contact]] = Future{
    def contact = ctx.run( quote {query[Contact].filter(_.id == lift(id))})
    Some(Contact(contact.head.id,contact.head.firstName,contact.head.lastName,contact.head.email,contact.head.phone))
  }

  def getAllContacts():Future[Option[Vector[Contact]]] = Future{

    val getAllContacts = ctx.run(quote {query[Contact]})

    Some(getAllContacts.toVector)}

  def updateContact(id:Int, update:ContactUpdate):Future[Option[Contact]]={

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

  def deletedContact(id:Int):Future[Unit]=Future{
    ctx.run(quote {query[Contact].filter(_.id == lift(id)).delete})
  }
}
