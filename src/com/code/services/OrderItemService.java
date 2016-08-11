package com.code.services;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.code.dal.orm.*;

public class OrderItemService {
	
	public static List<OrderItemView> getOrderListByOrderID( Long orderId)
	{  try{
		SessionFactory  sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		Query query = session.getNamedQuery("OrderItemByOrder");
		query.setLong("OrderId",orderId);
		List <OrderItemView> result = (List<OrderItemView>)query.list();
		session.close();
        return result;
	    }catch(Exception ea){return null;}
	}
	
	public static List<OrderItemView> getOrderItemListByUserId(Long userId)
	{  try{
		SessionFactory  sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		Query query = session.getNamedQuery("OrderItemByUser");
		query.setLong("UserId",userId);
		List <OrderItemView> result = (List<OrderItemView>)query.list();
		session.close();
        return result;
	    }catch(Exception ea){return null;}
	}
	
	

	public static List<User> getAllUsers( )
	{  try{
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		List <User> result = session.createCriteria(User.class).list();
		session.close();
        return result;
	    }catch(Exception ea){return null;}
	}
	
	public static Float itemPrice(Long Item)
	{  try{
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		PlacesItem obj =(PlacesItem) session.get(PlacesItem.class, Item);
		session.close();
        return obj.getPrice();
	    }catch(Exception ea){return null;}
	}
	
	
	public static Order getOrderByOrderID(Long orderId)
	{
		try{
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
			Order order =(Order) session.get(Order.class, orderId);
			session.close();
	        return order;
		    }catch(Exception ea){return null;}
	}
	
	public static Place getPlaceByPlaceID(Long placeId)
	{
		try{
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
			Place orderPlace =(Place) session.get(Place.class, placeId);
			session.close();
	        return orderPlace;
		    }catch(Exception ea){return null;}
	}
	
	public static String orderItemDeleteManually(OrderItemView ordItemView)
	{
		try{	
		if(ordItemView == null)
		return "Error . Please refresh the page to retrieve updated data";
		Order ord = getOrderByOrderID(ordItemView.getOrderId());
		if(ord == null)
		return "Error . Please refresh the page to retrieve updated data";
		if(ord.getStatus().toLowerCase().trim().equals("open"))
		{
			OrderItem ordItem = new OrderItem();
			ordItem.setId(ordItemView.getId());
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
		    session.beginTransaction();
		    session.delete(ordItem);
		    session.getTransaction().commit();
		    session.close();
			return null;
		}
		else return "Order isn't open . if the page says otherwise . Please refresh it";
		}catch(Exception ea){return "Error . Please refresh the page to retrieve updated data or try again";}
	}
	
	
	public static String orderItemAddManually(OrderItemView ordItemView)
	{
		try{	
		if(ordItemView == null)
		return "Error . Please refresh the page to retrieve updated data";
		Order ord = getOrderByOrderID(ordItemView.getOrderId());
		if(ord == null)
		return "Error . Please refresh the page to retrieve updated data";
		if(ord.getStatus().toLowerCase().trim().equals("open"))
		{
			OrderItem ordItem = new OrderItem();
			ordItem.setCount(ordItemView.getCount());
			ordItem.setItem(ordItemView.getItemId());
			ordItem.setOrder(ord.getId());
			ordItem.setUser(ordItemView.getUserId());
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
		    session.beginTransaction();
		    Long id =(Long)session.save(ordItem);
		    ordItemView.setId(id);
		    session.getTransaction().commit();
		    session.close();
			return null;
		}
		else return "Order isn't open . if the page says otherwise . Please refresh it";
		}catch(Exception ea){return "Error . Please refresh the page to retrieve updated data or try again";}
	}
	
	
	
	public static String orderItemUpdateManually(OrderItemView ordItemView)
	{
		try{	
		if(ordItemView == null)
		return "Error . Please refresh the page to retrieve updated data";
		Order ord = getOrderByOrderID(ordItemView.getOrderId());
		if(ord == null)
		return "Error . Please refresh the page to retrieve updated data";
		if(ord.getStatus().toLowerCase().trim().equals("open"))
		{
			OrderItem ordItem = new OrderItem();
			ordItem.setCount(ordItemView.getCount());
			ordItem.setItem(ordItemView.getItemId());
			ordItem.setOrder(ord.getId());
			ordItem.setUser(ordItemView.getUserId());
			ordItem.setId(ordItemView.getId());
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
		    session.beginTransaction();
		    session.update(ordItem);
		    session.getTransaction().commit();
		    session.close();
			return null;
		}
		else return "Order isn't open . if the page says otherwise . Please refresh it";
		}catch(Exception ea){return "Error . Please refresh the page to retrieve updated data or try again";}
	}
	
	
	
	
	
	
	public static List<PlacesItem> getMenuListByOrderID( Long PlaceId)
	{  
		try{	
		SessionFactory orderItemSessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = orderItemSessionFactory.openSession();
		String hql = "from com.code.dal.orm.PlacesItem AS PI where PI.placeId = :ID  ";
		List <PlacesItem> result = session.createQuery(hql).setParameter("ID", PlaceId).list();
        return result;
	    }catch(Exception ea){return null;}
	}
	
}
