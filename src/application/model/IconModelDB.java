package application.model;

import javafx.scene.image.ImageView;



/**
 * 
 * Was muss ein DBIcon zusätslich haben, definieren wir in diese Klasse
 * Diese Klasse hat schon alle von IconModel Klasse(Wir machen diese Klasse von IconModel extend) 
 * username, password...
 */


public class IconModelDB extends IconModel {

	private String userName;

	private String password;

	private String netmask;

	private String db;

	private String table;
	
	public IconModelDB(ImageView imageView,String type,String id,double x,double y) {
        super(imageView, type, id,x,y);
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

}
