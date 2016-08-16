package com.code.services;
import java.util.List;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.code.dal.orm.*;
public class Service 
{
	
	private static SessionFactory SF = new Configuration().configure().buildSessionFactory();
	 
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
   public static void Add(String itemName,String itemDescription,Float price,Long placeID)
   {
			Session S=SF.openSession();
			S.beginTransaction();
			PlacesItem placeItem=new PlacesItem();
			placeItem.setName(itemName);
			placeItem.setDescription(itemDescription);
			placeItem.setPrice(price);
			placeItem.setPlaceId(placeID);
			S.save(placeItem);
			S.getTransaction().commit();
			S.close();
   }
   public static List<PlacesItem> Search(String itemName,Float priceFrom,Float priceTo,Long placeID)
   {
	   Session S=SF.openSession();
	   S.beginTransaction();
	   Query Q=S.getNamedQuery("SearchItems");
	   Q.setParameter("itemName",itemName);
	   Q.setParameter("priceFrom",priceFrom);
	   Q.setParameter("priceTo",priceTo);
	   Q.setParameter("placeID", placeID);
	   List<PlacesItem> PI =(List<PlacesItem>)Q.list();
	   S.getTransaction().commit();
	   S.close();
	   return PI;
   }
   public static List<PlacesItem> getAllPlaceItems()
   {
	   Session S=SF.openSession();
	   S.beginTransaction();
	   Query Q=S.getNamedQuery("getAllItems");
	   List<PlacesItem>PI=(List<PlacesItem>)Q.list();
	   S.getTransaction().commit();
	   S.close();
	   return PI;
   }
	   
}
