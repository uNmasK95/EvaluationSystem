package model; /**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Universidade do Minho
 * License Type: Academic
 */
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class UserDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression ID;
	public final StringExpression email;
	public final StringExpression password;
	public final StringExpression firstName;
	public final StringExpression lastName;
	public final BooleanExpression registered;
	public final BooleanExpression deleted;
	public final StringExpression registrationCode;
	public final CollectionExpression _notifications;
	
	public UserDetachedCriteria() {
		super(User.class, UserCriteria.class);
		ID = new IntegerExpression("ID", this.getDetachedCriteria());
		email = new StringExpression("email", this.getDetachedCriteria());
		password = new StringExpression("password", this.getDetachedCriteria());
		firstName = new StringExpression("firstName", this.getDetachedCriteria());
		lastName = new StringExpression("lastName", this.getDetachedCriteria());
		registered = new BooleanExpression("registered", this.getDetachedCriteria());
		deleted = new BooleanExpression("deleted", this.getDetachedCriteria());
		registrationCode = new StringExpression("registrationCode", this.getDetachedCriteria());
		_notifications = new CollectionExpression("ORM__notifications", this.getDetachedCriteria());
	}
	
	public UserDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, UserCriteria.class);
		ID = new IntegerExpression("ID", this.getDetachedCriteria());
		email = new StringExpression("email", this.getDetachedCriteria());
		password = new StringExpression("password", this.getDetachedCriteria());
		firstName = new StringExpression("firstName", this.getDetachedCriteria());
		lastName = new StringExpression("lastName", this.getDetachedCriteria());
		registered = new BooleanExpression("registered", this.getDetachedCriteria());
		deleted = new BooleanExpression("deleted", this.getDetachedCriteria());
		registrationCode = new StringExpression("registrationCode", this.getDetachedCriteria());
		_notifications = new CollectionExpression("ORM__notifications", this.getDetachedCriteria());
	}
	
	public NotificationDetachedCriteria create_notificationsCriteria() {
		return new NotificationDetachedCriteria(createCriteria("ORM__notifications"));
	}
	
	public User uniqueUser(PersistentSession session) {
		return (User) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public User[] listUser(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (User[]) list.toArray(new User[list.size()]);
	}
}

