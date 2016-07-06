package controllers;

import java.util.List;

import javax.inject.Inject;

import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

public class UserController extends Controller {

	@Inject
	FormFactory formFactory;

	public Result listUsers() {
		List<User> allUsers = User.findAll();
		Form<User> userForm = formFactory.form(User.class);
		return ok(views.html.user.render(allUsers, userForm));
	}

	public Result create() {
		Form<User> userForm = formFactory.form(User.class).bindFromRequest();
		if (userForm.hasErrors()) {
			List<User> allUsers = User.findAll();
			return ok(views.html.user.render(allUsers, userForm));
		}
		User user = userForm.get();
		user.save();
		flash("success", String.format("User %s erfolgreich erstellt.", user.getName()));
		return redirect(routes.UserController.listUsers());
	}

}
