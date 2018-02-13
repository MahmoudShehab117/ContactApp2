package BackEnd.services

import BackEnd.DatabaseSchema.{Contacts, Phones, databaseEntities}
import BackEnd.entity.{Contact, ContactDetails, Phone}
import slick.dbio.Effect
import slick.jdbc
import slick.jdbc.H2Profile

import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.H2Profile.api._
import slick.sql.FixedSqlStreamingAction

import scala.collection.immutable

class ContactService(implicit  val executionContext : ExecutionContext) {

  val h2Database = databaseEntities.db

//  def getContact(id :Int):Future[Option[Contact]] = {
//    h2Database.run(databaseEntities.contacts.filter(_.id === id).result).map(_.headOption)
//  }

  def getAllContacts():Future[Seq[Contact]] = {
            /*Quill Code*/
      //      def Allcontact = ctx.run( quote {query[Contact]})
      //      Some(Allcontact.toVector)
      //      db.run(contacts.result).map(_.headOption)
            /*Slick Code*/

//    val query = for {
//      (contact, phones) <- databaseEntities.contacts joinLeft databaseEntities.phones on (_.id === _.contactID)
//    } yield (contact, phones)

//    databaseEntities.db.stream(query.result).foreach(println)

    h2Database.run(databaseEntities.contacts.result)
    }


  def createContact(contact: Contact): Future[Contact] = {
    val insert = databaseEntities.contacts returning databaseEntities.contacts.map(_.id) into ((cont, id) => cont.copy(id = id))
    h2Database.run(insert += contact)
  }

  def createPhone(id:Int,contactDetails: ContactDetails): (Future[Seq[Phone]]) = {
    val insert = databaseEntities.phones returning databaseEntities.phones.map(_.id) into ((cont, id) => cont.copy(id = id))
    val phones: Seq[Phone] = contactDetails.phones.map{case Some(t)=> t}

    val newPhones = phones.map(_.copy(contactID = id))

    h2Database.run(insert ++= newPhones)
  }

  def deleteContact(id:Int):Future[Int]={
    h2Database.run(databaseEntities.contacts.filter(_.id === id).delete)
    h2Database.run(databaseEntities.phones.filter(_.contactID === id).delete)
    //  ctx.run(quote {query[Contact].filter(_.id == lift(id)).delete})
  }

  def deletePhones(id:Int):Future[Int]={
    h2Database.run(databaseEntities.phones.filter(_.contactID === id).delete)
    //  ctx.run(quote {query[Contact].filter(_.id == lift(id)).delete})
  }

  def updateContact(id:Int, updateContact:Contact):Future[Contact]={

    val firstName = updateContact.firstName
    val lastName = updateContact.lastName
    val email = updateContact.email

    h2Database.run(databaseEntities.contacts.filter(_.id === id)
      .map(x => (x.firstName,x.lastName,x.email))
      .update(firstName,lastName,email)
    )
    Future(updateContact)

  }


  def getContactDetails(id :Int): Future[Option[ContactDetails]] = {
    val contact = databaseEntities.contacts.filter(_.id===id)

    val query = for {
      (contact, phones) <- contact joinLeft databaseEntities.phones on (_.id === _.contactID)
    } yield (contact, phones)

      val result: Future[Seq[(Contact, Option[Phone])]] = h2Database.run(query.result)
      val queryResult: Future[Option[ContactDetails]] = result map { sequence =>
  //      sequence groupBy { case (contact, _) => contact} map {
  //        case (contact, groupBySeq) => ContactDetails(contact, groupBySeq.map(_._2))
  //      } headOption
            sequence.groupBy(_._1).map {
          case (contact, groupBySeq) => ContactDetails(contact, groupBySeq.map(_._2))
        }.headOption
      }
//    result.foreach(println)
//    queryResult.foreach(println)
    queryResult

  }

  def createContactDetails(contactDetails: ContactDetails)= {
    val tx = createContact(contactDetails.contact).flatMap { contact: Contact => createPhone(contact.id,contactDetails) }
    Future(contactDetails)

  }

  def updateContactDetails(id:Int, updateContact:ContactDetails):Future[ContactDetails]={

    val firstName = updateContact.contact.firstName
    val lastName = updateContact.contact.lastName
    val email = updateContact.contact.email

    h2Database.run(databaseEntities.contacts.filter(_.id === id)
      .map(x => (x.firstName,x.lastName,x.email))
      .update(firstName,lastName,email)
    )
    deletePhones(id) map {_ => createPhone(updateContact.contact.id,updateContact)}

    Future(updateContact)
  }

  /*
    def createContact(contact:Contact):Future[Option[Contact]]=Future{

      val findContact = ctx.run( quote {query[Contact].filter(_.id == lift(contact.id))}).headOption
        findContact match {
        case Some(_)=> None
        case None => ctx.run(quote {query[Contact].insert(lift(contact))})
          Some(contact)
      }
    }

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
              deleteContact(id).flatMap {_ => createContact(updatedContact).map(_ => Some(updatedContact))}
      }
    }}

      def deleteContact(id:Int):Future[Unit]=Future{
       //db.run(this.filter(_.id === id).delete)
        ctx.run(quote {query[Contact].filter(_.id == lift(id)).delete})
      }

    */

}
