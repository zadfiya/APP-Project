package com.project.model;

public class Bookmarked {
	private int id;
	private int coinid;
	private double purchasedPrice;
	private double quantity;
	private String purchasedDate;
	private String insertDate;
	private String updateDate;
	private String name;
	
	public Bookmarked()
	{
		 id=0;
		 coinid=0;
		 purchasedPrice=0;
		 quantity=0;
		 purchasedDate="";
		 insertDate="";
		 updateDate="";
		 name="";
	}
	public Bookmarked(double purchasedPrice,double quantity,String purchasedDate,String updatedDate,int id)
	{
		this.setId(id);
		this.setPurchasedPrice(purchasedPrice);
		this.setQuantity(quantity);
		this.setPurchasedDate(purchasedDate);
		this.setUpdateDate(updatedDate);
	}
	
	public Bookmarked(int coinid,double purchasedPrice,double quantity,String purchasedDate,String insertDate)
	{
		this.setCoinid(coinid);
		this.setPurchasedPrice(purchasedPrice);
		this.setQuantity(quantity);
		this.setPurchasedDate(purchasedDate);
		this.setInsertDate(insertDate);
		this.setUpdateDate(insertDate);
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCoinid() {
		return coinid;
	}

	public void setCoinid(int coinid) {
		this.coinid = coinid;
	}

	public double getPurchasedPrice() {
		return purchasedPrice;
	}

	public void setPurchasedPrice(double purchasedPrice) {
		this.purchasedPrice = purchasedPrice;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(String purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
