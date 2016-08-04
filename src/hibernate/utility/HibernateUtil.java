package hibernate.utility;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil
{
	private static SessionFactory MySessionFactory;

	static {
	 MySessionFactory =  new Configuration().configure().buildSessionFactory();

	}
	
	
	public static SessionFactory getMySessionFactory() {
		return MySessionFactory;
	}

	
}
