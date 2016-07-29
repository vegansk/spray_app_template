import argonaut._, Argonaut._

package object models {

  case class Account(id: Int, name: String, docTypeId: Option[Int])

  object Account {
    implicit def AccountCodecJson: CodecJson[Account] =
      casecodec3(Account.apply, Account.unapply)("id", "name", "docTypeId")
  }

}
