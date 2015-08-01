package controllers

import javax.inject.Inject

import models.User
import play.api._
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.mvc._
import play.api.i18n.{I18nSupport, MessagesApi}

class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def index = Action {
    Ok(views.html.index(userForm))
  }

  def list = Action { implicit request =>
    val users = User.findAll
    Ok(views.html.users.list(users))
  }

  def show(username: String) = Action { implicit request =>
    User.findByUsername(username).map { user =>
      Ok(views.html.users.details(user))
    }.getOrElse(NotFound)
  }


  private val userForm: Form[User] = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(User.apply)(User.unapply)
  )

  def signUp = Action { implicit request =>
    val newUser = userForm.bindFromRequest()
    newUser.fold(
      hasErrors = { form =>
        Redirect(routes.Application.index())
      },
      success = { user =>
        User.signUp(user)
        Redirect(routes.Application.show(user.username))
      }
    )
  }
}
