package pkg.folder;

import java.util.List;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.GsonBuilder;

import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Represents a collection of books or (sub)collections.
 */
public final class Collection extends Element {
    @Expose 
    List<Element> elements;
    @Expose 
    private String name;
    
    /**
     * Creates a new collection with the given name.
     *
     * @param name the name of the collection
     */
    public Collection(String name) {
	this.name = name;
	this.elements = new ArrayList<Element>();
	this.setParentCollection(null);
    }

    /**
     * Restores a collection from its given string representation.
     *
     * @param stringRepresentation the string representation
     */
    public static Collection restoreCollection(String stringRepresentation) {
	// {"elements":[{"elements":[{"title":"MyBook","author":"Author"}],"name":"MyCol2"}],"name":"MyCol1"} 
	Collection c;
	JsonParser parser = new JsonParser();
	JsonElement el = parser.parse(stringRepresentation);
	//if (el.isJsonObject()) {
	JsonObject ob = el.getAsJsonObject();
	
	if (ob.get("name") != null) {
	    c = new Collection(ob.get("name").getAsString());
	    JsonArray arr = ob.getAsJsonArray("elements");
	
	    for (int i = 0; i < arr.size(); i++) {
		JsonElement inner = parser.parse(arr.get(i).toString());
		JsonObject iOb = inner.getAsJsonObject();
		
		// check to see if it's a book
		if (iOb.get("title") != null) {
		    //System.out.println("found a book");
		    Book b = new Book(iOb.get("title").getAsString(), iOb.get("author").getAsString());
		    c.addElement(b);
		} 
		if (iOb.get("name") != null) {
		    //System.out.println("found a subcollection");
		    Collection ci = Collection.restoreCollection(arr.get(i).toString());
		    c.addElement(ci);
		}
	    }
	
	    return c;
	} else {
	    return null;
	}
    }

    /**
     * Returns the string representation of a collection. 
     * The string representation should have the name of this collection, 
     * and all elements (books/subcollections) contained in this collection.
     *
     * @return string representation of this collection
     */
    public String getStringRepresentation() {
	GsonBuilder builder = new GsonBuilder();
	Gson gson = builder.excludeFieldsWithoutExposeAnnotation().create();
	return gson.toJson(this);
    }

    /**
     * Returns the name of the collection.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Adds an element to the collection.
     * If parentCollection of given element is not null,
     * do not actually add, but just return false.
     * (explanation: if given element is already a part of  
     * another collection, you should have deleted the element 
     * from that collection before adding to another collection)
     *
     * @param element the element to add
     * @return true on success, false on fail
     */
    public boolean addElement(Element element) {
	if (element.getParentCollection() != null) {
	    return false;
	} else {
	    element.setParentCollection(this);
	    this.elements.add(element);
	    return true;
	}
    }
    
    /**
     * Deletes an element from the collection.
     * Returns false if the collection does not have 
     * this element.
     * Hint: Do not forget to clear parentCollection
     * of given element 
     *
     * @param element the element to remove
     * @return true on success, false on fail
     */
    public boolean deleteElement(Element element) {
	for (int i = 0; i < this.elements.size(); i++) {
	    if (element.equals(this.elements.get(i))) {
		this.elements.get(i).setParentCollection(null);
		this.elements.remove(i);
		return true;
	    }
	}

        return false;
    }
    
    /**
     * Returns the list of elements.
     * 
     * @return the list of elements
     */
    public List<Element> getElements() {
        return elements;
    }
}
