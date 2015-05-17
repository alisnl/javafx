package application.model;


import javafx.scene.image.ImageView;






/**
 * 
 * Was muss ein Icon haben, definieren wir in diese Klasse 
 * z.B type=DB Oder PC oder ...
 * Welche Position x,y
 * Fleche w,h...
 * Ip...
 */
public class IconModel {
	

	public static final double W =32d;
	public static final double H =32d;
	
	//Type ist DB,PC
	private String  type ="";
	
	//Jedes Icon muss eine ID haben,
	private String  id ="";
	
	//Alle icon hat IP Address
	private String IP;
	
	
	
	private double x =0;
	private double y =0;
	
    private ImageView imageView;

	public IconModel(ImageView imageView,String type,String id,double x,double y) {
        setId(id);
		setImageView(imageView);
		setType(type);
		setX(x);
		setY(y);
		getImageView().setLayoutX(x);
		getImageView().setLayoutY(y);
	}

	

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	public String getIP() {
		return IP;
	}



	public void setIP(String iP) {
		IP = iP;
	}



	public double getX() {
		return x;
	}



	public void setX(double x) {
		this.x = x;
	}



	public double getY() {
		return y;
	}



	public void setY(double y) {
		this.y = y;
	}

	

}
