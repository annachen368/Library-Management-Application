package pkg.folder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import org.junit.Before;
import java.util.List;

public class BookTest {
    Book b1 = new Book("Title", "Author");
    Book b2 = new Book("{\"title\":\"MyBook\",\"author\":\"Author1, Author2, Author3\"}");
    Collection c1 = new Collection("MyCol1");
    Collection c2 = new Collection("MyCol2");
    Collection c3 = new Collection("MyCol3");

    @Before
    public void initialize() {
	b2.setParentCollection(c1);
	c1.setParentCollection(c2);
	c2.setParentCollection(c3);
    }

    @Test
    public void testBookConstructor1() {
	assertEquals("Author", b1.getAuthor());
    }
	
    @Test
    public void testBookConstructor2() {
	assertEquals("Title", b1.getTitle());
    }
    
    @Test
    public void testBookConstructor3() {
	assertEquals("MyBook", b2.getTitle());
    }
    
    @Test
    public void testBookConstructor4() {
	assertEquals(null, b1.getContainingCollections());
    }

    @Test
    public void testGetStringRepresentation1() {
	assertEquals("{\"title\":\"MyBook\",\"author\":\"Author1, Author2, Author3\"}", b2.getStringRepresentation());
    }
	
    @Test
    public void testGetStringRepresentation2() {
	assertEquals("{\"title\":\"Title\",\"author\":\"Author\"}", b1.getStringRepresentation());
    }

    @Test
    public void testGetContainingCollections1() {
	assertEquals(null, b1.getContainingCollections());
    }

    @Test
    public void testGetContainingCollections2() {
	assertEquals("MyCol1", b2.getContainingCollections().get(0).getName());
    }

    @Test
    public void testGetContainingCollections3() {
	assertEquals("MyCol2", b2.getContainingCollections().get(1).getName());
    }

    @Test
    public void testGetContainingCollections4() {
	assertEquals(3, b2.getContainingCollections().size());
    }

    @Test
    public void testGetParentCollection1() {
	assertEquals(null, b1.getParentCollection());
    }

    @Test
    public void testGetParentCollection2() {
	assertEquals(c1, b2.getParentCollection());
    }

    @Test
    public void testGetParentCollection3() {
        assertEquals(c3, c2.getParentCollection());
    }

    @Test
    public void testSetParentCollection1() { // via getParent
	b1.setParentCollection(c1);
        assertEquals(c1, b1.getParentCollection());
    }
}
