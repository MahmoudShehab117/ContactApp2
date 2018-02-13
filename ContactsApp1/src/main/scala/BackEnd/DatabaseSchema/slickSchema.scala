package BackEnd.DatabaseSchema

import BackEnd.entity.{Contact, Phone, Report, Users}
import slick.driver.PostgresDriver.api._

class Contacts(tag: Tag) extends Table[Contact](tag, "CONTACT") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def firstName = column[String]("FIRSTNAME")
  def lastName = column[String]("LASTNAME")
  def email = column[String]("EMAIL")

  def * = (id, firstName,lastName,email) <> ((Contact.apply _).tupled, Contact.unapply _)
}

class UserEntity(tag: Tag) extends Table[Users](tag, "USERS") {

  def userName = column[String]("USERNAME")
  def password = column[String]("PASSWORD")

  def * = (userName, password) <> ((Users.apply _).tupled, Users.unapply _)
}

class Phones(tag: Tag) extends Table[Phone](tag, "Phones") {

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def contactID = column[Int]("CONTACTID")
  def phone = column[String]("PHONE")

  def contact = foreignKey("CONT_FK", contactID, databaseEntities.contacts)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
  def * = (id, contactID,phone) <> ((Phone.apply _).tupled, Phone.unapply _)
}

class Reports(tag: Tag) extends Table[Report](tag, "Reports") {

  def contactCount = column[Int]("CONTACTCOUNT")
  def contactHasPhones = column[Int]("CONTACTHASPHONES")
  def contactNotHasPhones = column[Int]("CONTACTNOTHASPHONES")

  def * = (contactCount, contactHasPhones,contactNotHasPhones) <> ((Report.apply _).tupled, Report.unapply _)
}