package cmd;
//fkotoni21
public class Item<K, V> 
{
	/* data fields */
	private K key;
	private V val;
	
	/* constructor */
	public Item(K key, V val)
	{
		this.key = key;
		this.val = val;
	}
	
	/* getters */
	public K getKey()
	{
		return key;
	}
	public V getVal()
	{
		return val;
	}
	
	/* overridden equals method */
	public boolean equals(Item<K,V> item)
	{
		if (!this.key.equals(item.key))
			return false;
		return this.val.equals(item.val);
	}
	
	/* overridden display method */
	public String toString() 
	{
		return "(" + key.toString() + ", " + val.toString() + ")";
	}	
}
