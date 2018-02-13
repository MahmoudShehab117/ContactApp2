package BackEnd.DatabaseSchema

import BackEnd.entity.{Contact, Phone, Report}
import slick.jdbc.H2Profile.api._

import scala.concurrent.{ExecutionContext, Future}

object databaseEntities {

  val db = Database.forConfig("database2")
  val contacts = TableQuery[Contacts]
  val users = TableQuery[UserEntity]
  val phones = TableQuery[Phones]
  val reports = TableQuery[Reports]

  def recreateSchema(): Unit = {

    val schema = contacts.schema ++ users.schema ++ phones.schema ++ reports.schema

    val insertInit = Seq(Contact(1,"Mahmoud","Shehab","Shehab@hmou.com"),
                          Contact(2,"Ali","youssef","ali@jnjf.com")
                          ,Contact(3,"shrief","hossam","hossam@hh.conn"))

    val insertInit2 = Seq(Phone(1,1,"12343"),
      Phone(1,1,"234")
      ,Phone(1,1,"123"),Phone(1,2,"2322224"),Phone(1,2,"2344444"),Phone(1,1,"23334"))

    println("Perparing DB  .........")

    for (s <- schema.createStatements) {
      try {
        db.createSession().withPreparedInsertStatement(s)(_.execute())
      } catch {
        case e: Throwable => e.printStackTrace()
      }
    }

    val insertContact = databaseEntities.contacts returning databaseEntities.contacts.map(_.id) into ((cont, id) => cont.copy(id = id))
    db.run(insertContact ++= insertInit)

    val insertPhone = databaseEntities.phones returning databaseEntities.phones.map(_.id) into ((phone, id) => phone.copy(id = id))
    db.run(insertPhone ++= insertInit2)

  }


}
