package cmd;
//fkotoni21
import java.util.LinkedList;
public class MyHashMap<K, V> 
{
	/* data fields */
	private LinkedList<Item<K,V>>[] data;
	private int size;
	
	/* constructor */
	public MyHashMap(int size)
	{
		this.size = size;
		data = (LinkedList<Item<K,V>>[]) new LinkedList[size];
	}
		
	public V get(K key)
	{
		int hash = key.hashCode() % size;
		if (data[hash] == null) //check if linked list is empty
			return null;
		
		LinkedList<Item<K,V>> itemList = data[hash];
		for (Item<K,V> item: itemList) //check every item in the list
		{
			if (item.getKey().equals(key))
				return item.getVal(); //return value if the keys match
		}
		return null;
	}
	
	public void put(K key, V val)
	{
		int hash = key.hashCode() % size;
		if (data[hash] == null) //create new linked list if there isn't one in the current array ref list
			data[hash] = new LinkedList<Item<K,V>>();
		
		LinkedList<Item<K,V>> itemList = data[hash];
		Item<K,V> item = new Item<K,V>(key, val);
		itemList.add(item);
	}
	
	public void remove(K key)
	{
		int hash = key.hashCode() % size;
		if (data[hash] == null) //terminate method if there is nothing to remove
			return;
		
		LinkedList<Item<K,V>> itemList = data[hash];
		LinkedList<Item<K,V>> tmpList = new LinkedList<Item<K,V>>(); //create backup list
		
		for (Item<K,V> item: itemList) //check every item in the list
		{
			if (!item.getKey().equals(key))
				tmpList.add(item); //only add items whose keys don't match to that list
		}
		
		//if array is empty, make current array ref list null - otherwise, add backup list to array
		data[hash] = (tmpList.size() == 0) ? null : new LinkedList<>(tmpList);
	}
	
	public void printList()
	{
		for (int i=0; i<size; i++)
		{
			LinkedList<Item<K,V>> itemList = data[i];
			if (itemList!=null)
			{
				for (Item<K,V> item: itemList)
					System.out.println(item + " located at index "+i);
			}
		}
	}
}
