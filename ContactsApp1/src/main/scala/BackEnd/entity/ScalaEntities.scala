package BackEnd.entity

case class Contact(id:Int , firstName:String , lastName:String , email:String)
case class ContactDetails( contact:Contact, phones : Seq[Option[Phone]] )
case class Phone (id :Int, contactID :Int , phone : String)
case class Report(contactCount : Int,contactHasPhones : Int,contactNotHasPhones : Int)
case class Users (userName:String , password:String)
