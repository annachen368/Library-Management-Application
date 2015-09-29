package pkg.folder;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Container class for all the collections (that eventually contain books). Its
 * main responsibility is to save the collections to a file and restore them
 * from a file.
 */
public final class Library {
    @Expose
    private List<Collection> collections;

    /**
     * Builds a new, empty library.
     */
    public Library() {
	this.collections = new ArrayList<Collection>();
    }

    /**
     * Builds a new library and restores its contents from the given file.
     *
     * @param fileName
     *            the file from where to restore the library.
     */
    public Library(String fileName) {
	this.collections = new ArrayList<Collection>();
	try {
	    JsonParser parser = new JsonParser();
	    JsonElement el = parser.parse(new FileReader(fileName));
	    JsonObject root = el.getAsJsonObject();
	    JsonArray lib = root.getAsJsonArray("collections");

	    for(int i=0; i < lib.size(); i++) {
		JsonElement inner = parser.parse(lib.get(i).toString());
                JsonObject iOb = inner.getAsJsonObject();

		if (iOb.get("name") != null) {
		    this.collections.add(Collection.restoreCollection(lib.get(i).toString()));
		}
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException ioe) {
	    ioe.printStackTrace();
	}
    }
    
    /**
     * Saved the contents of the library to the given file.
     *
     * @param fileName
     *            the file where to save the library
     */
    public void saveLibraryToFile(String fileName) {
	GsonBuilder builder = new GsonBuilder();
	Gson gson = builder.excludeFieldsWithoutExposeAnnotation().create();
	
	try { // Writing to a file
	    File file = new File(fileName);
	    file.createNewFile();
	    FileWriter fileWriter = new FileWriter(file);
	    fileWriter.write(gson.toJson(this));

	    fileWriter.flush();
	    fileWriter.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
    }
    
    /**
     * Returns the collections contained in the library.
     *
     * @return library contained elements
     */
    public List<Collection> getCollections() {
	return collections;
    }
}
