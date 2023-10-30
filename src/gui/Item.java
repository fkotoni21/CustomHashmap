package gui;
//fkotoni21
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item 
{
	//data fields (string properties)
	private final StringProperty word = new SimpleStringProperty();
    private final StringProperty def = new SimpleStringProperty();
    
    /* default constructor */
    public Item() 
    {
    	
    }
    
    /* constructor w/ data fields as params */
    public Item(String w, String d) 
    {
    	word.set(w); 
    	def.set(d);
    }
    
    /* getters and setters */
    public String getWord() 
    {
    	return (word.getValue() != null) ?word.get():"";
    }
    
    public void setWord(String index)
    {
    	this.word.set(index);
    }
    
    public String getDef() 
    {
    	return (def.getValue() != null) ?def.get():"";
    }
    
    public void setDef(String d) 
    {
    	this.def.set(d);
    }
    
    /* getters for string properties */
    public StringProperty wordProperty() 
    {
    	return word;
    }
    
    public StringProperty defProperty() 
    {
    	return def;
    }
    
	/* overridden display method */
	public String toString() 
	{
		return "(" + word.getValue() + ", " + def.getValue() + ")";
	}	
}
