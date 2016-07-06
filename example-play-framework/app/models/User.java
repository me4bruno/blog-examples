package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import play.Logger;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

@Entity
@Table(name = "Users")
public class User extends Model {

	private static final Logger.ALogger logger = Logger.of(User.class);

	private static Find<Long, User> find = new Find<Long, User>() {
	};

	public static List<User> findAll() {
		List<User> users = find.all();
		return users;
	}

	public static User findByEmail(String email) {
		User user = find.where().eq("email", email).findUnique();
		return user;
	}

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<ValidationError>();

		User userWithEmail = findByEmail(email);
		if (userWithEmail != null) {
			logger.info("Email bereits vorhanden: " + email);
			errors.add(new ValidationError("email", "Email bereits vorhanden."));
		}

		return errors.isEmpty() ? null : errors;
	}

	@Id
	Long id;

	@CreatedTimestamp
	private Date createdAt;

	@UpdatedTimestamp
	private Date updatedAt;

	@Version
	private Timestamp version;

	@Required
	String email;

	@Required
	String name;

	public User(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
