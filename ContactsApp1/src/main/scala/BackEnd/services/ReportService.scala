package BackEnd.services

import BackEnd.DatabaseSchema.databaseEntities.phones
import BackEnd.DatabaseSchema.{Contacts, databaseEntities}
import slick.jdbc.H2Profile.api._
import BackEnd.entity.{Contact, ContactDetails, Phone, Report}
import slick.dbio.Effect
import slick.jdbc
import slick.jdbc.H2Profile
import slick.sql.{FixedSqlAction, FixedSqlStreamingAction}

import scala.concurrent.{ExecutionContext, Future}


class ReportService(implicit  val executionContext : ExecutionContext) {

  val h2Database = databaseEntities.db

  def getReport(): Future[Report] = {

    import databaseEntities._

    val contactLength: FixedSqlAction[Int, H2Profile.api.NoStream, Effect.Read] = databaseEntities.contacts.length.result

//      FixedSqlAction[Int, jdbc.H2Profile.api.NoStream, Effect.Read]
//      FixedSqlAction[Int, _root_.slick.jdbc.H2Profile.api.NoStream, Effect.Read]
    val contactHasPhonesQueryAction: FixedSqlAction[Int, _root_.slick.jdbc.H2Profile.api.NoStream, Effect.Read] =( for (
      (contact,phones) <- contacts join phones on (_.id === _.contactID)
    ) yield contact).groupBy(_.id).map{case (id,group)=>(id) }.drop(0).length.result


//    val contactNotHasPhonesQueryAction: FixedSqlAction[Int, jdbc.H2Profile.api.NoStream, Effect.Read] = (for (
//      (contact,phones) <- contacts join phones on (_.id != _.contactID)
//    ) yield contact).groupBy(_.id).map{case (id,group)=>(id) }.drop(0).length.result

    val contactNotHasPhonesQueryAction: FixedSqlAction[Int, _root_.slick.jdbc.H2Profile.api.NoStream, Effect.Read] =(for {
      a <- contacts if !phones.filter(b => b.contactID === a.id).exists
    } yield (a)).groupBy(_.id).map{case (id,group)=>(id) }.drop(0).length.result


    val query2: DBIOAction[Report, NoStream, Effect.Read with Effect.Read with Effect.Read] = contactLength.map { c1 =>
      contactHasPhonesQueryAction.map { c2 => contactNotHasPhonesQueryAction.map{c3 => Report(c1, c2, c3)}  }
    }.flatten.flatten

    h2Database.run(phones.result)
    h2Database.run(query2)
    //Try with future:

//      first Solution*******
//    val finalCountAction: DBIOAction[Report, NoStream, Effect.Read with Effect.Read with Effect] = contactLength.flatMap { firstCount =>
//      contactHasPhonesQueryAction flatMap { secondCount =>
//        //DBIO.successful(Report(firstCount, secondCount, 0))
//
//        Report(0, 0, 0)
//      }
//    }

//      Second Solution *******
//    val f1 = db.run(contactLength)
//    val f2 = db.run(contactHasPhonesQueryAction)
//
//    f1 flatMap { v1 =>
//
//      print("inside v1")
//
//      f2 flatMap { v2 =>
//
//        print("inside v2")
//
//        Future(Report(v1, v2, 0))
//      }
//    }

  }

}
