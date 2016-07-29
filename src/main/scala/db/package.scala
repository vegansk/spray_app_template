import models._
import scalaz._, Scalaz._

package object db {

  def getAccount(id: Int): Account =
    Account(id, "test", 1.some)

}
