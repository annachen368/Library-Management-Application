# Library-Management-Application

### Book

To keep things simple, books will contain only the title and the author/authors, where "author" can encode multiple people, possibly with some special characters to separate people. (We won't have publisher, year, number of pages, etc.). Both title and author will be represented as simple strings. A book can be exported to and imported from a string representation. 

### Collection
The Collection class represents a single collection. It contains a name of the collection and also has a list of references to every book and every subcollection in that particular collection. A collection can be exported to and imported from a string representation. 

### Element
The Element class is the superclass of both Book and Collection. It contains a reference to the parent collection that directly contains this element. An element can directly belong to at most one parent collection, but that collection can belong to other collections, so an element can indirectly belong to several collections. 

### Library
The library object is just a container for all collections. A library can be exported to or imported from a file. 

## Tests
JUnit test for each of the following methods:

### Book.java
Book(String stringRepresentation) // regular constructor
String getStringRepresentation()
List<Collection> getContainingCollections()

### Collection.java
static Collection restoreCollection(String stringRepresentation) // static method instead of constructor
String getStringRepresentation()
boolean addElement(Element element) // see comments in code for some conditions
boolean deleteElement(Element element) // see comments in code for some conditions

### Library.java
saveLibraryToFile(String fileName)
Library(String fileName)
