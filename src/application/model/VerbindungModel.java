package application.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * 
 * Was muss eine Verbindungslinie haben, definieren wir in diese Klasse 
 * Die Linie muss zwischen 2 icons sein, das ist eigentlich zwischen 2 Punkten...
 * Diese Punkten ist A und B,
 * Der PunktA ist von die Position des IconA und PunktB ist von die Position des IconB
 *  
 * und jeder Punkt muss ein x und ein y  Wert haben.... ax,ay und bx,by
 * 
 */

public class VerbindungModel {
	
	private String idA="";
	private String idB="";
	
	
	private double ax =0;
	private double ay =0;
	
	private double bx =0;
	private double by =0;
	
	
	public VerbindungModel() {
	}
	
	public String getIdA() {
		return idA;
	}
	public void setIdA(String idA) {
		this.idA = idA;
	}
	public String getIdB() {
		return idB;
	}
	public void setIdB(String idB) {
		this.idB = idB;
	}
	public double getAx() {
		return ax;
	}
	public void setAx(double ax) {
		this.ax = ax;
	}
	public double getAy() {
		return ay;
	}
	public void setAy(double ay) {
		this.ay = ay;
	}
	public double getBx() {
		return bx;
	}
	public void setBx(double bx) {
		this.bx = bx;
	}
	public double getBy() {
		return by;
	}
	public void setBy(double by) {
		this.by = by;
	}
	public Line getLine() {
		Line line=new Line(ax,ay,bx,by);
		
		return line;
	}
	
	
	
	
	
}
