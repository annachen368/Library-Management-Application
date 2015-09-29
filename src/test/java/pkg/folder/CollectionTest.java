package pkg.folder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import org.junit.Before;
import java.util.List;

public class CollectionTest {
    Book b1 = new Book("Title1", "Author");
    Book b2 = new Book("{\"title\":\"Title2\",\"author\":\"Author1, Author2, Author3\"}");
    Book b3 = new Book("Title3", "Author4, Author5");
    Collection c1 = new Collection("MyCol1");
    Collection c2 = new Collection("MyCol2");
    Collection c3 = new Collection("MyCol3");
    String json1 = "{\"elements\":[{\"elements\":[{\"title\":\"MyBook\",\"author\":\"Author\"}],\"name\":\"MyCol2\"}],\"name\":\"MyCol1\"}";
    String json2 = "{\"elements\":[{\"elements\":[{\"title\":\"MyBook1\",\"author\":\"Author1\"}],\"name\":\"MyCol2\"},{\"title\":\"MyBook2\",\"author\":\"Author1, Author2\"}],\"name\":\"MyCol1\"}";
    String json3 = "{\"elements\":[{\"title\":\"MyBook\",\"author\":\"Author\"}],\"nameABC\":\"MyCol1\"}";

    //          c3
    //         /
    //       c2
    //      / | \
    //    c1 b2 b3
    //   / 
    // b1
    @Before
    public void initialize() {
	//c1.addElement(b1);
	//c2.addElement(b2);
	//c2.addElement(b3);
	//c2.addElement(c1);
	//c3.addElement(c2);
    }

    @Test
    public void testRestoreCollection1() {
	Collection c = Collection.restoreCollection(json1);

	assertEquals("MyCol1", c.getName());
    }
    
    @Test
    public void testRestoreCollection2() {
	Collection c = Collection.restoreCollection(json2);
	Collection temp = (Collection)c.getElements().get(0);

	assertEquals("MyCol2", temp.getName());
    }
    
    @Test
    public void testRestoreCollection3() {
	Collection c = Collection.restoreCollection(json2);
	
	assertEquals("{\"elements\":[{\"elements\":[{\"title\":\"MyBook1\",\"author\":\"Author1\"}],\"name\":\"MyCol2\"},{\"title\":\"MyBook2\",\"author\":\"Author1, Author2\"}],\"name\":\"MyCol1\"}", c.getStringRepresentation());
    }

    @Test
    public void testRestoreCollection4() {
	Collection c = Collection.restoreCollection(json3);
	
	assertEquals(null, c);
    }

    @Test
    public void testGetStringRepresentation1() {
	c1.addElement(b1);
	assertEquals("{\"elements\":[{\"title\":\"Title1\",\"author\":\"Author\"}],\"name\":\"MyCol1\"}", c1.getStringRepresentation());
    }
    
    @Test
    public void testGetStringRepresentation2() {
	c1.addElement(b1);
        c2.addElement(b2);
        c2.addElement(b3);
        c2.addElement(c1);
        c3.addElement(c2);

	assertEquals("{\"elements\":[{\"elements\":[{\"title\":\"Title2\",\"author\":\"Author1, Author2, Author3\"},{\"title\":\"Title3\",\"author\":\"Author4, Author5\"},{\"elements\":[{\"title\":\"Title1\",\"author\":\"Author\"}],\"name\":\"MyCol1\"}],\"name\":\"MyCol2\"}],\"name\":\"MyCol3\"}", c3.getStringRepresentation());
    }
    
    @Test
    public void testCollectionConstructor1() {
	Collection c = new Collection("MyCol");
	
	assertEquals("MyCol", c.getName());
    }
    
    @Test
    public void testAddElement1() {
	c1.addElement(b1);
	Collection c = new Collection("MyCol");

	assertEquals(false, c.addElement(b1));
    }

    @Test
    public void testAddElement2() {
	Collection c = new Collection("MyCol");
	Book b = new Book("MyBook", "Author");
	
	assertTrue(c.addElement(b));
    }
    
    @Test
    public void testAddElement3() {
	c1.addElement(b1);
        c2.addElement(b2);
        c2.addElement(b3);
        c2.addElement(c1);
        c3.addElement(c2);	

	assertEquals(3, c2.getElements().size());
    }
    
    @Test
    public void testDeleteElement1() {
	c1.addElement(b1);

	assertEquals(false, c1.deleteElement(b2));
    }

    @Test
    public void testDeleteElement2() {
	c1.addElement(b1);

	assertTrue(c1.deleteElement(b1));
    }

    @Test
    public void testDeleteElement3() {
	c1.addElement(b1);
        c2.addElement(b2);
        c2.addElement(b3);
        c2.addElement(c1);
        c3.addElement(c2);

	assertTrue(c3.deleteElement(c2));
    }

    @Test
    public void testDeleteElement4() {
	c1.addElement(b1);
        c2.addElement(b2);
        c2.addElement(b3);
        c2.addElement(c1);
        c3.addElement(c2);
	
	c3.deleteElement(c2);
	
	assertEquals(null, c2.getParentCollection());
    }

    @Test
    public void testDeleteElements5() {
        c2.addElement(b2);
        c2.addElement(b3);
        c2.addElement(c1);

	c2.deleteElement(b3);

	assertEquals(2, c2.getElements().size());
    }

    @Test
    public void testDeleteElements6() {
        c2.addElement(b2);
        c2.addElement(b3);
        c2.addElement(c1);
	
	c2.deleteElement(b3);

	assertEquals("{\"elements\":[{\"title\":\"Title2\",\"author\":\"Author1, Author2, Author3\"},{\"elements\":[],\"name\":\"MyCol1\"}],\"name\":\"MyCol2\"}", c2.getStringRepresentation());
    }
}
