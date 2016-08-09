package Controller;
import java.util.List;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.code.dal.orm.*;
public class Service 
{
	 
	public static List<PlacesItem> getItemsList(Long PlaceID)
	{
		SessionFactory   SF=new Configuration().configure().buildSessionFactory();
		Session S=SF.openSession();
		S.beginTransaction();
		Query Q=S.getNamedQuery("getItems");
		Q.setLong(0,PlaceID);
		List<PlacesItem> PI =(List<PlacesItem>)Q.list();
		S.getTransaction().commit();
		S.close();
		return PI;
	}
}
