package com.code.services;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.code.OrderStatusEnum;
import com.code.dal.orm.*;

public class OrderItemService {
	private SessionFactory sessionFactory = null;
	private static OrderItemService orderItemService = null;

	private OrderItemService() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	public static OrderItemService getSingleton() {
		if (orderItemService == null) {
			orderItemService = new OrderItemService();
		}
		return orderItemService;
	}

	public List<OrderItemView> getOrderListByOrderID(Long orderId) {
		try {

			Session session = sessionFactory.openSession();
			Query query = session.getNamedQuery("OrderItemByOrder");
			query.setLong("OrderId", orderId);
			List<OrderItemView> result = (List<OrderItemView>) query.list();
			session.close();
			return result;
		} catch (Exception ea) {
			return null;
		}
	}

	public List<OrderItemView> getOrderItemListByUserId(Long userId) {
		try {

			Session session = sessionFactory.openSession();
			Query query = session.getNamedQuery("OrderItemByUser");
			query.setLong("UserId", userId);
			List<OrderItemView> result = (List<OrderItemView>) query.list();
			session.close();
			return result;
		} catch (Exception ea) {
			return null;
		}
	}

	public Float itemPrice(Long Item) {
		try {

			Session session = sessionFactory.openSession();
			PlacesItem obj = (PlacesItem) session.get(PlacesItem.class, Item);
			session.close();
			return obj.getPrice();
		} catch (Exception ea) {
			return null;
		}
	}

	public Order getOrderByOrderID(Long orderId) {
		try {
			if (orderId == null)
				orderId = -1L;
			Session session = sessionFactory.openSession();
			Order order = (Order) session.get(Order.class, orderId);
			session.close();
			return order;
		} catch (Exception ea) {
			return null;
		}
	}

	public Place getPlaceByPlaceID(Long placeId) {
		try {

			Session session = sessionFactory.openSession();
			Place orderPlace = (Place) session.get(Place.class, placeId);
			session.close();
			return orderPlace;
		} catch (Exception ea) {
			return null;
		}
	}

	public String orderItemDeleteManually(OrderItemView ordItemView) {
		try {
			if (ordItemView == null)
				return "Error . Please refresh the page to retrieve updated data";
			Order ord = getOrderByOrderID(ordItemView.getOrderId());
			if (ord == null)
				return "Error . Please refresh the page to retrieve updated data";
			if (ord.getStatus().toLowerCase().trim()
					.equals(OrderStatusEnum.OPENED.getCode())
					|| ord.getStatus().toLowerCase().trim()
							.equals(OrderStatusEnum.CANCELED.getCode())) {
				OrderItem ordItem = new OrderItem();
				ordItem.setId(ordItemView.getId());
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				session.delete(ordItem);
				session.getTransaction().commit();
				session.close();
				return null;
			} else
				return "Order isn't open . if the page says otherwise . Please refresh it";
		} catch (Exception ea) {
			return "Error . Please refresh the page to retrieve updated data or try again";
		}
	}

	public String orderItemAddManually(OrderItemView ordItemView) {
		try {
			if (ordItemView == null)
				return "Error . Please refresh the page to retrieve updated data";
			Order ord = getOrderByOrderID(ordItemView.getOrderId());
			if (ord == null)
				return "Error . Please refresh the page to retrieve updated data";
			if (ord.getStatus().toLowerCase().trim()
					.equals(OrderStatusEnum.OPENED.getCode())) {
				OrderItem ordItem = new OrderItem();
				ordItem.setCount(ordItemView.getCount());
				ordItem.setItem(ordItemView.getItemId());
				ordItem.setOrder(ord.getId());
				ordItem.setUser(ordItemView.getUserId());
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				Long id = (Long) session.save(ordItem);
				ordItemView.setId(id);
				session.getTransaction().commit();
				session.close();
				return null;
			} else
				return "Order isn't open . if the page says otherwise . Please refresh it";
		} catch (Exception ea) {
			return "Error . Please refresh the page to retrieve updated data or try again";
		}
	}

	public List<OrderItemView> getOrderedItemsFiltered(Long orderId,
			String itemName, String username) {
		if (orderId == null)
			orderId = -1L;
		if (itemName == null || itemName.equals("") )
			itemName = "-1";
		if (username == null || username.equals(""))
			username = "-1";
		try {

			Session session = sessionFactory.openSession();
			Query query = session.getNamedQuery("FilterOrderItemView");
			query.setLong("OrderId", orderId).setString("Username", username)
					.setString("ItemName", itemName);
			List<OrderItemView> result = (List<OrderItemView>) query.list();
			session.close();
			return result;

		} catch (Exception ea) {
			return null;
		}
	}

	public String orderItemUpdateManually(OrderItemView ordItemView) {
		try {
			if (ordItemView == null)
				return "Error . Please refresh the page to retrieve updated data";
			Order ord = getOrderByOrderID(ordItemView.getOrderId());
			if (ord == null)
				return "Error . Please refresh the page to retrieve updated data";
			if (ord.getStatus().toLowerCase().trim()
					.equals(OrderStatusEnum.OPENED.getCode())) {
				OrderItem ordItem = new OrderItem();
				ordItem.setCount(ordItemView.getCount());
				ordItem.setItem(ordItemView.getItemId());
				ordItem.setOrder(ord.getId());
				ordItem.setUser(ordItemView.getUserId());
				ordItem.setId(ordItemView.getId());
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				session.update(ordItem);
				session.getTransaction().commit();
				session.close();
				return null;
			} else
				return "Order isn't open . if the page says otherwise . Please refresh it";
		} catch (Exception ea) {
			return "Error . Please refresh the page to retrieve updated data or try again";
		}
	}

	public List<PlacesItem> getMenuListByOrderID(Long PlaceId) {
		try {
			Session session = sessionFactory.openSession();
			String hql = "from com.code.dal.orm.PlacesItem AS PI where PI.placeId = :ID  ";
			List<PlacesItem> result = session.createQuery(hql)
					.setParameter("ID", PlaceId).list();
			return result;
		} catch (Exception ea) {
			return null;
		}
	}

}
