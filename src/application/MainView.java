package application;



import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import application.model.IconModelDB;
import application.model.IconModel;
import application.model.MittelPaneManager;
import application.model.VerbindungModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;









/**
 * 
 * Diese Klasse ist der Kontroller von MainView.fxml
 * Mit dem javaFX scene builder erstellen wir MainView.fxml und wir verbinden dann MainView.fxml und MainView.java
 * 
 *
 */
public class MainView implements Initializable {


	@FXML
	private TextField ipField;

	@FXML
	private TextField userField;

	@FXML
	private TextField passwordField;

	@FXML
	private TextField netmaskField;

	@FXML
	private TextField dbField;

	@FXML
	private TextField tableField;

	@FXML
	private ImageView iconEdit;

	@FXML
	private ImageView iconPR;

	@FXML
	private ImageView iconPC;

	@FXML
	private ImageView iconDB;

	@FXML
	private ImageView iconBook;

	@FXML
	private ImageView iconBack;

	@FXML
	private ImageView iconForward;

	@FXML
	private ImageView iconOk;

	@FXML
	private AnchorPane paneMittel;

	@FXML
	private GridPane paneDBInfo;
	
	@FXML
	private GridPane paneInfoButton;


	@FXML
	private Button buttonDelete;
	
	@FXML
	private Button buttonSave;

	@FXML
	private GridPane paneIPInfo;

	@FXML
	private VBox paneLinks;

	@FXML
	private VBox paneRecht;

    private MittelPaneManager mittelPanaManager;   
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		
		setMittelPanaManager(new MittelPaneManager(this));
		
		paneRecht.getChildren().clear();
		
		
		iconDB.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {
					paneRecht.getChildren().clear();
					paneRecht.getChildren().add(paneDBInfo);
				}

			}
		});
  
		iconEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				
				if (mittelPanaManager.getMode().equals(MittelPaneManager.MODE_FREE)) {

					mittelPanaManager.setMode(MittelPaneManager.MODE_VERBINDUNG);
					Image icon = new Image(MainView.class.getResourceAsStream("/application/editaktiv.png"));
					iconEdit.setImage(icon);

				} else {
					mittelPanaManager.setMode(MittelPaneManager.MODE_FREE);
					Image image = new Image(MainView.class.getResourceAsStream("/application/editpassiv.png"));
						
					iconEdit.setImage(image);
				}

			}
		});
		
       

		iconDB.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				Dragboard db = iconDB.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putString("DB");
				db.setContent(content);
				mouseEvent.consume();
			}
		});

		iconPC.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				Dragboard db = iconPC.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putString("PC");
				db.setContent(content);
				mouseEvent.consume();
			}
		});
		
		
		iconBook.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				Dragboard db = iconBook.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putString("book");
				db.setContent(content);
				mouseEvent.consume();
			}
		});

		iconPR.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				Dragboard db = iconPR.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putString("PR");
				db.setContent(content);
				mouseEvent.consume();
			}
		});

		

		paneMittel.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				dropped(event);
			}

		});
		paneMittel.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {

				if (event.getGestureSource() != paneMittel
						&& event.getDragboard().hasString()) {
					/* allow for both copying and moving, whatever user chooses */
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                      over(event);
//					refreshView();
				}

				event.consume();
			}
		});
		
		
		
		
		buttonSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				
				
				IconModel icon=mittelPanaManager.getSelectedIcon();
				if (icon!=null) {

					icon.setIP(ipField.getText());
					
					if (icon.getType().equals("DB")) {
						((IconModelDB)icon).setUserName( userField.getText());
						((IconModelDB)icon).setPassword( passwordField.getText());
						((IconModelDB)icon).setNetmask( netmaskField.getText());
						((IconModelDB)icon).setDb( dbField.getText());
						((IconModelDB)icon).setTable( tableField.getText());
					}
				}

			}
		});


		buttonDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				
				
				IconModel icon=mittelPanaManager.getSelectedIcon();
				mittelPanaManager.deleteIcon(icon);
				refreshView();
				paneRecht.getChildren().clear();

			}
		});
		
		
	}

	/**
	 *  Nach jedem Prosses muss man  Display aktualisieren.........
	 */
	
	public void refreshView() {

		paneMittel.getChildren().clear();

		mittelPanaManager.updateVerbindungenLocale();

		List<VerbindungModel> vbs = mittelPanaManager.getVerbindungs();

		for (VerbindungModel verbindung : vbs) {

			paneMittel.getChildren().add(verbindung.getLine());
		}

		Collection<IconModel> icons = mittelPanaManager.getIcons().values();

		for (Iterator<IconModel> iterator = icons.iterator(); iterator.hasNext();) {
			IconModel modelBase = (IconModel) iterator.next();
			paneMittel.getChildren().add(modelBase.getImageView());

		}
		
//		if (mittelPanaManager.getSelectedIconA()!=null) {
//			IconModel selectedIcon = mittelPanaManager.getSelectedIconA();
//			double x=selectedIcon.getX()-1;
//			double y=selectedIcon.getY()-1;
//			double w=IconModel.W+2;
//	        
//			
//			Line line1=new Line(x,y,x+w,y);
//
//			Line line2=new Line(x+w,y,x+w,y+w);
//
//			Line line3=new Line(x,y+w,x+w,y+w);
//
//			Line line4=new Line(x,y,x,y+w);
//			
//			paneMittel.getChildren().addAll(line1,line2,line3,line4);
//		} 
		
	

	}


	

	/**
	 * Wenn wir eine Icon mit Mause in mittlere Feld lassen, lauft diese Methode...
	 * 
	 * @param event
	 */
	private void dropped(DragEvent event) {
		Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasString()) {

			ImageView imageView = null;
			
			//x und y beschreiben ein Punkt ,wo die Mause derzeit auf mitlere Feld liegt........ 
			 
			double x=event.getX()-IconModel.W/2;
            
			double y=event.getY()-IconModel.W/2;
            		
			if (db.getString().equals("DB")) {//Icon Type ist DB
				imageView = new ImageView(iconDB.getImage());
				mittelPanaManager.addIconOnMittelPane("DB", imageView,
						x,y);
			} else if (db.getString().equals("PC")) {
				imageView = new ImageView(iconPC.getImage());
				mittelPanaManager.addIconOnMittelPane("PC", imageView,
						x,y);
			} else if (db.getString().equals("PR")) {
				imageView = new ImageView(iconPR.getImage());
				mittelPanaManager.addIconOnMittelPane("PR", imageView,
						x,y);
			} else if (db.getString().contains("book")) {
				imageView = new ImageView(iconBook.getImage());
				mittelPanaManager.addIconOnMittelPane("book", imageView,x,y);
                
			} else if (db.getString().contains("ID")) {
				//Das ist wichtig weil  diese Icon ein ID hat, d.h diese Icon ist schon vorher in mittlere Feld gezogen. 
			    //Wir wollen nur die Position des Icons veränderen.......
				
				mittelPanaManager.updateIconLocale(db.getString(),x,y);
                
			} else {

			}
			refreshView();
			success = true;
		}

		event.setDropCompleted(success);

		event.consume();
	}
	
	
	/**
	 * 
	 * Wenn wir eine Icon mit Mause  in mittlere Feld   NOCH NICHT lassen, lauft diese Methode...
	 * 
	 * @param event
	 */
	private void over(DragEvent event) {
		Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasString()) {

			ImageView imageView = null;
            double x=event.getX()-IconModel.W/2;
            double y=event.getY()-IconModel.W/2;
             if (db.getString().contains("ID")) {
            	 
            	 //Wenn diese Icon vorher in mittlere Feld gezogen ist,machen wir mit diesem Methode für jede Mauspositions diese Iconsposition aktualisieren 
            	
            	 IconModel iconModel=mittelPanaManager.getIcon(db.getString());
            	 iconModel.setX(x);
            	 iconModel.setY(y);
            	 iconModel.getImageView().setLayoutX(x);
            	 iconModel.getImageView().setLayoutY(y);
             	
			}	
             refreshView();
		}

	}
	
	
	
	/**
	 * Wenn wir in mittlere Feld ein Icon anklicken,muss das Programm in das rechte Feld passende Eingabefeldern zeigen 
	 */
	public void infoPaneOpen() {
        IconModel model=mittelPanaManager.getSelectedIcon();
		
		paneRecht.getChildren().clear();
		
	
		paneRecht.getChildren().add(new ImageView(model.getImageView().getImage()));
		paneRecht.getChildren().add(new Label(" "+model.getId()+""));
		paneRecht.getChildren().add(paneIPInfo);
		
		ipField.setText(model.getIP());
		
		if (model.getType().equals("DB")) {//für DB Icon brauchen wir zusätzliche Eingabefeldern
			paneRecht.getChildren().add(paneDBInfo);
		    userField.setText(((IconModelDB)model).getUserName());
		    passwordField.setText(((IconModelDB)model).getPassword());
		    netmaskField.setText(((IconModelDB)model).getNetmask());
		    dbField.setText(((IconModelDB)model).getDb());
		    tableField.setText(((IconModelDB)model).getTable());
		}
		
		paneRecht.getChildren().add(paneInfoButton);
		
		
		
		
	}

	public MittelPaneManager getMittelPanaManager() {
		return mittelPanaManager;
	}

	public void setMittelPanaManager(MittelPaneManager mittelPanaManager) {
		this.mittelPanaManager = mittelPanaManager;
	}

	

}
