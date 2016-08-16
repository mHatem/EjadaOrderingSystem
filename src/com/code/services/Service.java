package com.code.services;
import java.util.List;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.code.dal.orm.*;
public class Service 
{
	
	private static SessionFactory SF = SessionFactorySingleton.getSingleton().getSessionFactory();
	 
	public static List<PlacesItem> getItemsList(Long PlaceID)
	{
		
		Session S=SF.openSession();
		S.beginTransaction();
		Query Q=S.getNamedQuery("getItems");
		Q.setLong(0,PlaceID);
		List<PlacesItem> PI =(List<PlacesItem>)Q.list();
		S.getTransaction().commit();
		S.close();
		return PI;
	}
   public static void Delete(Long placeItemID)
   {
		Session S=SF.openSession();
		S.beginTransaction();
		PlacesItem placeItem=(PlacesItem)S.get(PlacesItem.class,placeItemID.longValue());
		S.delete(placeItem);
		S.getTransaction().commit();
		S.close();
   }
   public static List <String>  getOrderStatus(Long ItemID)
   {
		Session S=SF.openSession();
		S.beginTransaction();
		Query Q=S.getNamedQuery("getOpenOrders");
		Q.setLong(0,ItemID);
		List <String> order=(List<String>)Q.list();
		
		S.getTransaction().commit();
		S.close();
		return order;
   }
   public static void Update(PlacesItem placeItem)
   {
	   try
	   {
		Session S=SF.openSession();
		S.beginTransaction();
		S.update(placeItem);
		S.getTransaction().commit();
		S.close();
	   }
	   catch(Exception ex)
	   {
		   System.out.println(ex.toString());
	   }
   }
   public static void Add(String itemName,String itemDescription,Float price)
   {
			Session S=SF.openSession();
			S.beginTransaction();
			PlacesItem placeItem=new PlacesItem();
			placeItem.setName(itemName);
			placeItem.setDescription(itemDescription);
			placeItem.setPrice(price);
			S.getTransaction().commit();
			S.close();
   }
   /*public static List<PlacesItem> Search(String itemName,Float priceFrom,Float priceTo)
   {
	   if(itemName== null)
	   {
		   itemName="-1";
	   }
	   if(priceFrom==null)
	   {
		   priceFrom=0F;
	   }
	   if(priceTo==null)
	   {
		   priceTo=100F;
	   }
   }*/

	// TODO: added by amr, please review
	public static List<PlacesItem> getAllPlaceItems() {
		Session session = SessionFactorySingleton.getSingleton().getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session.getNamedQuery("PlaceItem.all");
		List<PlacesItem> list = (List<PlacesItem>) query.list();

			session.getTransaction().commit();
		session.close();
		return list;
	}
}
