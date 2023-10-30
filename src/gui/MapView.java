package gui;
//fkotoni21
import java.util.AbstractMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.TableView;

public class MapView<K,V> extends TableView<Map.Entry<K, V>>
{
    private final ObservableList<Map.Entry<K,V>> obsList;
    private final ObservableMap<K,V> map;
    private final MapChangeListener<K,V> mapChange;
    private final ListChangeListener<Map.Entry<K,V>> listChange;
    
    public MapView(ObservableMap<K,V> map) 
    {
        this.map = map;
        obsList = FXCollections.observableArrayList(map.entrySet());
        setItems(obsList);
        
        mapChange = new MapChangeListener<K, V>() 
        {
            @Override
            public void onChanged(MapChangeListener.Change<? extends K, ? extends V> change) 
            {
                obsList.removeListener(listChange);
                if (change.wasAdded()) //add entry to list if it's been added to the map already
                    obsList.add(new AbstractMap.SimpleEntry(change.getKey(),change.getValueAdded()));
                if (change.wasRemoved()) //remove entry from list if it's been removed from the map
                {
                    for (Map.Entry<K,V> entry : obsList) //sadly, this requires linear time
                    {
                        if (entry.getKey().equals(change.getKey()))
                        {
                            obsList.remove(entry);
                            break;
                        }
                    }
                }
                obsList.addListener(listChange);
            }
        };
        
        listChange = (ListChangeListener.Change<? extends Map.Entry<K, V>> change)
        -> {
            map.removeListener(mapChange);
            while (change.next())
            {
                if (change.wasAdded()) //add entry to map if it's been added to the list already
                {
                	for (Map.Entry<K, V> entry: change.getAddedSubList())
                        map.put(entry.getKey(),entry.getValue());
                }
                if (change.wasRemoved())  //remove entry from list if it's been removed from the map
                {
                	for (Map.Entry<K, V> me: change.getRemoved())
                        map.remove(me.getKey());
                }
            }
            map.addListener(mapChange);
        };
        
        map.addListener(mapChange);
        obsList.addListener(listChange);
    }
    
    /* method that only allows adding entries that are distinct/unique (to prevent duplicate placeholders) */
    public void addUnique(K key, V value)
    {
        boolean check = false;
        //change the value if there's a duplicate key
        for (Map.Entry<K,V> entry: getItems())
        {
            if (entry.getKey().equals(key))
            {
                check = true;
                entry.setValue(value);
                break; 
            }
        }
        if (!check) //add new entry once there are no duplicates
            getItems().add(new AbstractMap.SimpleEntry<>(key,value));
    }
}
