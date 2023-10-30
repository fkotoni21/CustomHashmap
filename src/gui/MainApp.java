package gui;
//fkotoni21
import java.util.Map;
import java.util.Map.Entry;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application 
{
    @Override
    public void start(Stage stg0) 
    { 
        final ObservableMap<String, Item> obsMap = FXCollections.observableHashMap();        
        final MapView<String,Item> mapView = new MapView<String, Item>(obsMap);
 
        //placeholder inputs
        obsMap.put(String.valueOf(0), new Item("Vendetta","a blood feud in which the family of a murdered person seeks vengeance on the murderer or their family"));
        obsMap.put(String.valueOf(1), new Item("Vendetta","a prolonged bitter quarrel with or campaign against someone"));
        obsMap.put(String.valueOf(2), new Item("Visage","a person's face, with reference to the form or proportions of the features"));
        obsMap.put(String.valueOf(3), new Item("Visage","a person's facial expression"));
        obsMap.put(String.valueOf(4), new Item("Visage","the manifestation, image, or aspect of something"));
        
        
        final TableColumn<Map.Entry<String,Item>,String> keyCol = new TableColumn<Entry<String, Item>, String>("Key");
        keyCol.setCellValueFactory //apply unique keys to the table view
        (
            (TableColumn.CellDataFeatures<Map.Entry<String,Item>, String> p) 
            -> new SimpleStringProperty(p.getValue().getKey())
        );
        
        keyCol.setCellFactory(TextFieldTableCell.forTableColumn());
        keyCol.setOnEditCommit
        (	//upon editing a key, replace its old instance with a new one
        	(CellEditEvent<Map.Entry<String,Item>, String> t) -> 
        	{
        		final String oldKey = t.getOldValue();
        		final Item oldWord = obsMap.get(oldKey);
        		obsMap.remove(oldKey);
        		obsMap.put(t.getNewValue(),oldWord);
        	}
        );
        
        final TableColumn<Map.Entry<String,Item>,String> wordCol = new TableColumn<Entry<String, Item>, String>("Word");
        wordCol.setCellValueFactory //apply words to the table view
        (
            (TableColumn.CellDataFeatures<Map.Entry<String,Item>, String> p)
            -> new SimpleStringProperty(p.getValue().getValue().getWord())
        );
        wordCol.setCellFactory(TextFieldTableCell.forTableColumn()); //allow word column cells to be editable
 
        final TableColumn<Map.Entry<String,Item>,String> defCol = new TableColumn<Entry<String, Item>, String>("Definition");
        defCol.setCellValueFactory //apply definitions keys to the table view
        (
            (TableColumn.CellDataFeatures<Map.Entry<String,Item>, String> p)
            -> new SimpleStringProperty(p.getValue().getValue().getDef())
        );
        
        defCol.setCellFactory(TextFieldTableCell.forTableColumn()); //allow definition column cells to be editable
        defCol.setOnEditCommit
        (
        	(CellEditEvent<Map.Entry<String,Item>, String> t) -> 
        	{
        		t.getTableView().getItems().get(t.getTablePosition().getRow()).getValue().setDef(t.getNewValue());
        	}
        );
 
        mapView.getColumns().addAll(keyCol,wordCol, defCol);
        mapView.setEditable(true); //allow table cell editing (to overwrite placeholder values)
        mapView.getSelectionModel().setCellSelectionEnabled(true); //enable selection for word and definition columns
        mapView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //automatically adjust column sizes (each column is 1/3 of the screen)
 
        final Button addBtn = new Button("Insert New Word");
        addBtn.setOnAction
        (
        	(ActionEvent t) -> 
        	{	//insert placeholder word once (unless it is overwritten, like it should be)
        		obsMap.put("Placeholder Key", new Item("Placeholder Word","Placeholder Description"));
        	}
        );
 
        final Button delBtn = new Button("Delete Word");
        delBtn.setOnAction
        (
        	(ActionEvent t) -> 
        	{	
        		int index = mapView.getSelectionModel().getSelectedIndex();
        		if (index >= 0)
        		{
        			//delete any word that is selected on the table (deselected words have an index of -1 by default)
        			Map.Entry<String,Item> deleted = mapView.getItems().remove(mapView.getSelectionModel().getSelectedIndex());
        			//do the same not just for the table, but also for the hash map itself
        			obsMap.remove(deleted);
        		}
        	}
        );
        
        final Button debug = new Button("Debug");
        debug.setOnAction
        (
        	(ActionEvent t) -> 
        	{
        		System.out.println("[Map Entries]");
        		for (Map.Entry<String,Item> item : obsMap.entrySet())
        			System.out.println("Key: "+item.getKey()+", Value: "+item.getValue());

        		System.out.println("[List Entries]");
        		for (Map.Entry<String,Item> item : mapView.getItems())
        			System.out.println("Key: "+item.getKey()+", Value: "+item.getValue());
        		System.out.println("");
        	}
        );

        HBox bottom = new HBox();
        VBox top = new VBox();

        bottom.getChildren().addAll(addBtn,delBtn,debug);
        top.getChildren().addAll(mapView,bottom);
        VBox.setVgrow(mapView, Priority.ALWAYS); //make vertical box allocate the right amount of space
        Scene scene = new Scene(top, 1280, 720);
 
        stg0.setTitle("Custom HashMap (Dictionary)");
        stg0.setScene(scene);
        stg0.show();
    }
}
