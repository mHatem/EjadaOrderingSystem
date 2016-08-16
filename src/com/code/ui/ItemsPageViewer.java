package com.code.ui;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.code.dal.orm.PlacesItem;
import com.code.dal.orm.UserRole;
import com.code.services.Service;
import com.code.ui.user.Login;
@SuppressWarnings("serial")
@ManagedBean (name="item")
@ViewScoped
public class ItemsPageViewer implements Serializable
{
	IndexView IV=new IndexView();
	private String itemName;
	private Float priceFrom;
	private Float priceTo;
	private List <PlacesItem> itemList;
	private Long placeID;
	private boolean deleteflag=true; 
	private boolean addButtonClicked=false;
	private boolean updateButtonClicked=false;
	private boolean saveButtonClicked=false;
	private String itemNameUpdate;
	private String Description;
	private Float price;
	private List <String> order;
	public ItemsPageViewer() {
	String idParameter=((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("id");
	if(idParameter != null)
	{
	placeID=Long.valueOf(idParameter);
	itemList=Service.getItemsList(placeID.longValue());
	}
	else
	{
		itemList=Service.getItemsList(placeID.longValue());
	}
	 deleteflag=true;
	}
	public void Delete(PlacesItem placeItem)
	{
		order=Service.getOrderStatus(placeItem.getId());
		if(order.size()>0)
		{
			if(order.get(0).equals("opened"))
			{
				deleteflag=false;
			}
			else
			{
				deleteflag=true;	
			}
		}
		else
		{
			deleteflag=true;
		}
		if(deleteflag==true)
		{
		Service.Delete(placeItem.getId());
		itemList.remove(placeItem);
		}
	}
	public void Update(PlacesItem placeItem)
	{
		placeItem.setUpdateButtonClicked(true);
		
	}
	public void Save(PlacesItem placeItem)
	{
		Service.Update(placeItem);
		placeItem.setUpdateButtonClicked(false);
	}
	public void Add()
	{
		setAddButtonClicked(true);
		
	}
	public void save()
	{
		Service.Add(itemNameUpdate,Description,price,placeID);
		PlacesItem placeItem=new PlacesItem();
		placeItem.setName(itemNameUpdate);
		placeItem.setDescription(Description);
		placeItem.setPrice(price);
		placeItem.setPlaceId(placeID);;
		itemList.add(placeItem);
		setAddButtonClicked(false);
	}
	public void search()
	{
		if(itemName== null)
		   {
			   itemName="-1";
		   }
		   else
		   {
			   itemName='%'+itemName+'%';
		   }
		   if(priceFrom==null || priceTo <0)
		   {
			   priceFrom=-1F;
		   }
		   if(priceTo==null || priceTo <0)
		   {
			   priceTo=-1F;
		   }
		   itemList=Service.Search(itemName, priceFrom, priceTo,placeID);
	   }
	public boolean isItemAdmin()
	{
Map<String, Object> sessionMap=FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		
		String userRole = (String) sessionMap.get(Login.SESSION_KEY_USER_ROLE);
		return userRole.equals(UserRole.ADMIN_ITEMS);
	}
	public boolean isDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(boolean deleteflag) {
		
		this.deleteflag = deleteflag;
	}
	public Long getPlaceID() {
		return placeID;
	}
	public void setPlaceID(Long placeID) {
		this.placeID = placeID;
	}
	public Float getPriceFrom() {
		return priceFrom;
	}
	public void setPriceFrom(Float priceFrom) {
		this.priceFrom = priceFrom;
	}
	public Float getPriceTo() {
		return priceTo;
		}
	public void setPriceTo(Float priceTo) {
		this.priceTo = priceTo;
	}
	public List <PlacesItem> Search()
	{
		return itemList;
	}
	public IndexView getIV() {
		return IV;
	}
	public void setIV(IndexView iV) {
		IV = iV;
	}
	public boolean isUpdateButtonClicked() {
		return updateButtonClicked;
	}
	public void setUpdateButtonClicked(boolean updateButtonClicked) {
		this.updateButtonClicked = updateButtonClicked;
	}
	public boolean isSaveButtonClicked() {
		return saveButtonClicked;
	}
	public void setSaveButtonClicked(boolean saveButtonClicked) {
		this.saveButtonClicked = saveButtonClicked;
	}
	public String getItemNameUpdate() {
		return itemNameUpdate;
	}
	public void setItemNameUpdate(String itemNameUpdate) {
		this.itemNameUpdate = itemNameUpdate;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public List<String> getOrder() {
		return order;
	}
	public void setOrder(List<String> order) {
		this.order = order;
	}
	public List<PlacesItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<PlacesItem> itemList) {
		this.itemList = itemList;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public boolean isAddButtonClicked() {
		return addButtonClicked;
	}
	public void setAddButtonClicked(boolean addButtonClicked) {
		this.addButtonClicked = addButtonClicked;
	}
	}
