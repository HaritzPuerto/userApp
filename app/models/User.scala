package models

/**
 * Created by haritz on 1/08/15.
 */
case class User(username: String, password: String)

object User{
  var users = Set(
    User("Josh", "Josh"),
    User("Dave", "Dave")
  )

  def findAll = users.toList

  def findByUsername(u: String) = users.find(_.username == u)

  def signUp(user: User): Unit = {
    users = users + user
  }
}
