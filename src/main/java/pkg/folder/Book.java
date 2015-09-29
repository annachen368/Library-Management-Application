package pkg.folder;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.MalformedJsonException;

import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * This class contains the information needed to represent a book.
 */
public final class Book extends Element {
	@Expose 
	private String title;
	@Expose 
	private String author;

	/**
	 * Builds a book with the given title and author.
	 *
	 * @param title
	 *            the title of the book
	 * @param author
	 *            the author of the book
	 */
	public Book(String title, String author) {
		this.title = title;
		this.author = author;
		this.setParentCollection(null);
	}

	/**
	 * Builds a book from the string representation, which contains the title
	 * and author of the book.
	 *
	 * @param stringRepresentation
	 *            the string representation
	 */
        public Book(String stringRepresentation) {
	    JsonParser parser = new JsonParser();
	    JsonElement el = parser.parse(stringRepresentation);
	    JsonObject b = el.getAsJsonObject();
	    this.title = b.get("title").getAsString();
	    this.author = b.get("author").getAsString();
	}

	/**
	 * Returns the string representation of the given book. The representation
	 * contains the title and author of the book.
	 *
	 * @return the string representation
	 */
	public String getStringRepresentation() {
	    GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.excludeFieldsWithoutExposeAnnotation().create();
	    return gson.toJson(this);	    
	}

	/**
	 * Returns all the collections that this book belongs to directly and
	 * indirectly. Consider the following. (1) Computer Science is a collection.
	 * (2) Operating Systems is a collection under Computer Science. (3) The
	 * Linux Kernel is a book under Operating System collection. Then,
	 * getContainingCollections method for The Linux Kernel should return a list
	 * of these two collections (Operating Systems, Computer Science), starting
	 * from the direct collection to more indirect collections.
	 *
	 * @return the list of collections
	 */
	public List<Collection> getContainingCollections() {
	    List<Collection> col = new ArrayList<Collection>();
	    Element ob = this;
	    
	    if (ob.getParentCollection() == null) {
		return null;
	    } else {
		while (ob.getParentCollection() != null) {
		    col.add(ob.getParentCollection());
		    ob = ob.getParentCollection();
		}

		return col;
	    }
	}

	/**
	 * Returns the title of the book.
	 *
	 * @return the title
	 */
	public String getTitle() {
	    return this.title;
	}

	/**
	 * Returns the author of the book.
	 *
	 * @return the author
	 */
	public String getAuthor() {
	    return this.author;
	}
}
