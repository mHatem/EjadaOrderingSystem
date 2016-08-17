package com.code.services;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.code.OrderStatusEnum;
import com.code.dal.orm.*;

public class OrderItemService {
	private SessionFactory sessionFactory = null;
	private static OrderItemService orderItemService = null;

	private OrderItemService() {
		sessionFactory = SessionFactorySingleton.getSingleton()
				.getSessionFactory();
	}

	public static OrderItemService getSingleton() {
		if (orderItemService == null) {
			orderItemService = new OrderItemService();
		}
		return orderItemService;
	}

	public List<OrderItemView> getOrderListByOrderID(Long orderId) {
		try {

			if (orderId == null)
				orderId = -1L;
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

	public List<OrderItemView> getAllOrderItem() {
		try {
			Session session = sessionFactory.openSession();
			Query query = session.getNamedQuery("AllOrderItem");
			List<OrderItemView> result = (List<OrderItemView>) query.list();
			session.close();
			return result;
		} catch (Exception ea) {
			return null;
		}

	}

	public List<OrderItemView> getOrderItemListByUserId(Long userId) {
		try {

			if (userId == null)
				userId = -1L;
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
					.equals(OrderStatusEnum.OPENED.getCode())) {
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
		String inputItem ;
        String inputUser ;
		if (orderId == null)
			orderId = -1L;
		if (itemName == null || itemName.equals(""))
			inputItem = "-1";
		else inputItem = "%"+itemName+"%";
		if (username == null || username.equals(""))
			inputUser = "-1";
		else inputUser = "%"+username+"%";
		try {
			
			Session session = sessionFactory.openSession();
			Query query = session.getNamedQuery("FilterOrderItemView");
			query.setLong("OrderId", orderId).setString("Username", inputUser)
					.setString("ItemName", inputItem);
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


	// TODO: added by amr, please review
	public List<OrderItemView> getOrderItemByUserIdOrPlaceIdOrPlaceItemId(Long userId,
	                                                                      Long placeId,
	                                                                      Long placeItemId) {
		if (userId == null)
			userId = -1L;
		if (placeId == null)
			placeId = -1L;
		if (placeItemId == null)
			placeItemId = -1L;

		Session session = SessionFactorySingleton.getSingleton()
				.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session
				.getNamedQuery("OrderItemView.byUserIdOrPlaceIdOrPlaceItemId");
		query.setLong("userId", userId);
		query.setLong("placeId", placeId);
		query.setLong("placeItemId", placeItemId);

		List<OrderItemView> list = (List<OrderItemView>) query.list();

		session.getTransaction().commit();
		session.close();

		return list;
	}
}
