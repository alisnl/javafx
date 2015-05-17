package application.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import application.MainView;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;




public class MittelPaneManager {

	
	public static final String MODE_FREE = "FREE";
    
	
	//in diese Mode kann man zwischen 2 Icons eine Verbindungslinie erstellen. 
	public static final String MODE_VERBINDUNG = "VERBINDUNG";
    
	/**
	 * Wenn wir es wissen wollen ,welche Mode wir derzeit haben,können wir diese Variabel schauen...
	 * und wenn wir Mode verändern, müssen wir diese Variabel verändern........
	 *
	 */
	private String mode = MODE_FREE;

	
	/**
	 * In der Mitte platzierten Icons befinden sich in diese Map (iconMap)
	 * 
	 * Wenn eine Icon drag und drop in das  mittlere Feld gezogen wird, 
	 * muss das Programm diese Icon in diese Map hinzufügen, 
	 * weil das Programm wieder diese Icon braucht um etwas zu verändern.
	 * 
	 */
	
	private Map<String, IconModel> iconMap = new HashMap<>();
	
	
	
	/**
	 * Alle Verbindungslinien befinden sich in diese ArrayList (verbindungsLinieList)
	 * 
	 */
	private ArrayList<VerbindungModel> verbindungsLinieList = new ArrayList<>();
	
    /**
	* Für Jede Icon müssen wir ein einzigartig ID geben, deshalb brauchen wir diese Variabel.
	* Nachdem eine ID zu geben müssen wir diese Variabel um eins erhohen.
	*/
	private int countIconID = 0;

	
	MainView mainView;

	// wenn wir in "VERBINDUNGsmode" auf ein Icon anklicken--> diese Icon ist dann unser erstes Icon von der Verbindungslinie  
	private IconModel selectedIconA = null;
	//und das hier unser 2. Icon von der Verbindungslinie 
	private IconModel selectedIconB = null;

	//angeklickt icon in mittelPane, 
	private IconModel selectedIcon = null;

	
	
	public MittelPaneManager(MainView mainView) {
		this.mainView = mainView;

	}

	
	/**
	 * Das Programm benutzt diese Method um eine Icon in  mitte zu ziehen
	 */
	
	public void addIconOnMittelPane(String type, ImageView imageView, double x, double y) {

		IconModel icon;

		String id = "ID" + countIconID;

		countIconID++;

		
		if (type.equals("DB")) {//Icon type ist DB
			/**
			 * wir erstellen ein Icon von IconModelDB Class ,weil icon DB zusätzliche info hat... 
			 */
			icon = new IconModelDB(imageView, type, id, x, y);
		} else {//Icon type ist nicht DB
			
			icon = new IconModel(imageView, type, id, x, y);
		}

		
		
		/**
		 * Wenn man ein Icon ziehen,lauft diese Method
		 */
		icon.getImageView().setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				Dragboard db = icon.getImageView().startDragAndDrop(
						TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putString(icon.getId());
				db.setContent(content);
				mouseEvent.consume();
			}
		});
		
		
		
		/**
		 * Wenn man auf ein Icon klicken,lauft diese Method
		 */
		icon.getImageView().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
                
				setSelectedIcon(icon);

				mainView.infoPaneOpen();

				if (isVerbundungMode()) {

					verbindenAction(icon);
				}

			}
		});
		
		
        //Man muss jedes neue Icon in iconMap hinzufügen.... 
		iconMap.put(id, icon);

	}

	public IconModel getIcon(String id) {
		return getIcons().get(id);
	}

	public Map<String, IconModel> getIcons() {
		return iconMap;
	}

	public void setIcons(Map<String, IconModel> icons) {
		this.iconMap = icons;
	}

	public boolean isVerbundungMode() {
		return getMode().equals(MittelPaneManager.MODE_VERBINDUNG);
	}

	public int getVerbindungIndex(IconModel modelA, IconModel modelB) {
		int index = -1;
		for (int i = 0; i < getVerbindungs().size(); i++) {
			VerbindungModel verbindung = getVerbindungs().get(i);

			if ((verbindung.getIdA().equals(modelA.getId()) && verbindung
					.getIdB().equals(modelB.getId()))
					|| (verbindung.getIdA().equals(modelB.getId()) && verbindung
							.getIdB().equals(modelA.getId()))) {
				index = i;
				return index;
			}
		}
		return index;
	}

	public void addVerbindung(IconModel iconA, IconModel iconB) {

		VerbindungModel verbindung = new VerbindungModel();

		verbindung.setIdA(iconA.getId());

		verbindung.setIdB(iconB.getId());

		getVerbindungs().add(verbindung);

		updateVerbindungenLocale();
	}

	private void removeVerbindung(IconModel modelA, IconModel modelB) {
		verbindungsLinieList.remove(getVerbindungIndex(modelA, modelB));

	}

	public ArrayList<VerbindungModel> getVerbindungs() {
		return verbindungsLinieList;
	}

	
	/**
	 * Wenn die Position des Icons verändert wird,müssen wir alle Position von den Verbindungslinie aktualisieren... 
	 */
	public void updateVerbindungenLocale() {

		for (VerbindungModel verbindung : verbindungsLinieList) {

			IconModel iconA = getIcon(verbindung.getIdA());
			IconModel iconB = getIcon(verbindung.getIdB());

			verbindung.setAx(iconA.getX() + IconModel.W / 2);
			verbindung.setAy(iconA.getY() + IconModel.H / 2);
			verbindung.setBx(iconB.getX() + IconModel.W / 2);
			verbindung.setBy(iconB.getY() + IconModel.H / 2);

		}

	}
	
	/** 
	 * Die Position des Icons wird mit diesem Method verändert.
	 * x und y beschreiben ein Punkt ,wo die Mause derzeit auf mitlere Feld liegt........ 
	 * 
	 */
    
	public void updateIconLocale(String id, double x, double y) {
		IconModel icon = getIcon(id);
		icon.getImageView().setLayoutX(x);
		icon.getImageView().setLayoutY(y);
		icon.setX(x);
		icon.setY(y);

	}

	
	
	
	private void verbindenAction(IconModel icon) {
	
		if (getSelectedIconA() == null) {//Wenn erste selected Icon (selectedIconA) null ist, machen wir diese icon erstes Icon von der Verbindungslinie
			setSelectedIconA(icon);
			
		} else if (getSelectedIconA().getId().equals(icon.getId())) {
			//wenn auf ein Icon vorher angeklicken wird, machen wir alle unselected
			
			setSelectedIconA(null);
			setSelectedIconB(null);
			
		} else {//wenn wir eine selected icon haben und diese icon anderes ist, machen wir diese icon 2. Icon von der Verbindungslinie
			
			setSelectedIconB(icon);
            
			 //das ist noch nicht fertig, weil wir es kontrolieren müssen,dass es  vorher eine V.linie zwischen diese zwei icons gibt?
			
			if (getVerbindungIndex(getSelectedIconA(), getSelectedIconB()) == -1) {//
			// wenn es vorher keine V.L gibt, addieren wir eine neue Vlinie 
				addVerbindung(getSelectedIconA(), getSelectedIconB());
			} else {
				
				// wenn es vorher eine V.L gibt, müssen wir diese Vlinie löschen... 
				
				removeVerbindung(getSelectedIconA(), getSelectedIconB());
				
				
			}
			
			//und nachdem Löschen oder Addieren müssen wir alle selectedIcon löschen 
			setSelectedIconA(null);
			setSelectedIconB(null);
		}
		// Nach jedem Prosses muss man  Display aktualisieren.........
		mainView.refreshView();
	}

	public IconModel getSelectedIcon() {
		return selectedIcon;
	}

	public void setSelectedIcon(IconModel selectedIcon) {
		this.selectedIcon = selectedIcon;
	}

	public IconModel getSelectedIconA() {
		return selectedIconA;
	}

	public void setSelectedIconA(IconModel icon) {
		this.selectedIconA = icon;
	}

	public IconModel getSelectedIconB() {
		return selectedIconB;
	}

	public void setSelectedIconB(IconModel icon) {
		this.selectedIconB = icon;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public void deleteIcon(IconModel icon) {
	    //Wenn man ein Icon löschen will, muss man  dieses Icon  in "iconMap" remove mit key, (key ist id)
		iconMap.remove(icon.getId());
		
		/**
		 *Und dann man muss auch alle dazu gehörende Verbindungslinie in  "verbindungsLinieList" löschen... 
		 *Das ist ein bissi schwerig 
		 *1- Dafür erstellen wir ein neu List... "newVerbindungList"
		 *2- wir müssen alle  nicht dazu gehörende Verbindungslinie in diesem List hinzufügen
		 *3- dann diese List ist unser neue verbindungsLinieList 
		 */
		   
		//Dafür erstellen wir ein neu List... "newVerbindungList"
		ArrayList<VerbindungModel> newVerbindungList = new ArrayList<VerbindungModel>();

		for (int i = 0; i < verbindungsLinieList.size(); i++) {//wir schauen alle Linie
	
			VerbindungModel verbindung = verbindungsLinieList.get(i);
			
			if (!(verbindung.getIdA().equals(icon.getId())|| verbindung.getIdB().equals(icon.getId()))) {
                
				//wir müssen alle  nicht dazu gehörende Verbindungslinie in diesem List hinzufügen
				newVerbindungList.add(verbindung);

			}
		}
		
		//dann ist diese List unser neue verbindungsLinieList 
		verbindungsLinieList=newVerbindungList;
		
		//zuletzt alle selectet Icon müssen wir löschen
		setSelectedIcon(null);
		setSelectedIconA(null);
		setSelectedIconB(null);
	}

}
