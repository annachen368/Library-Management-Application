package pkg.folder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class LibraryTest {
    @Test
    public void testLibraryConstructor1() {
	Library lib = new Library();

	assertEquals(0, lib.getCollections().size());
    }
    
    @Test
    public void testLibraryConstructorFromFile1() {
        int s = 1;
        try {
            File tempFile = File.createTempFile("libTest1.json", ".tmp");
            BufferedWriter bufferedReader = new BufferedWriter(new FileWriter(tempFile));

            bufferedReader.write("{\"collections\":[]}");
            bufferedReader.close();

            Library lib = new Library(tempFile.getAbsolutePath());
            s = lib.getCollections().size();

            tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(0, s);
    }


    @Test
    public void testLibraryConstructorFromFile2() {
	int s = 0;
	try {
	    File tempFile = File.createTempFile("libTest2.json", ".tmp");
	    BufferedWriter bufferedReader = new BufferedWriter(new FileWriter(tempFile));
	    
	    bufferedReader.write("{\"collections\":[{\"elements\":[{\"elements\":[{\"title\":\"MyBook1\",\"author\":\"Author1\"}],\"name\":\"MyCol2\"},{\"title\":\"MyBook2\",\"author\":\"Author1, Author2\"}],\"name\":\"MyCol1\"},{\"elements\":[{\"title\":\"MyBook4\",\"author\":\"Author7, Author8\"}],\"name\":\"MyCol4\"}]}");
	    bufferedReader.close();
	    
	    Library lib = new Library(tempFile.getAbsolutePath());
	    s = lib.getCollections().size();

	    tempFile.deleteOnExit();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
    	assertEquals(2, s);
    }

    @Test
    public void testLibraryConstructorFromFile3() {
        int s = 0;
        try {
            File tempFile = File.createTempFile("libTest2.json", ".tmp");
            BufferedWriter bufferedReader = new BufferedWriter(new FileWriter(tempFile));

            bufferedReader.write("{\"collections\":[{\"Book\":\"Random\",\"author\":\"Rando\"},{\"elements\":[{\"elements\":[{\"title\":\"MyBook1\",\"author\":\"Author1\"}],\"name\":\"MyCol2\"},{\"title\":\"MyBook2\",\"author\":\"Author1, Author2\"}],\"name\":\"MyCol1\"},{\"elements\":[{\"title\":\"MyBook4\",\"author\":\"Author7, Author8\"}],\"name\":\"MyCol4\"}]}");
            bufferedReader.close();

            Library lib = new Library(tempFile.getAbsolutePath());
            s = lib.getCollections().size();

            tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(2, s);
    }

    @Test
    public void testSaveLibraryToFile1() {
	String jsonIn  = "{\"collections\":[]}";
	String jsonOut = "";
	try {
            File tempFileRead = File.createTempFile("libTest3.json", ".tmp");
            BufferedWriter bufferedReader = new BufferedWriter(new FileWriter(tempFileRead));

            bufferedReader.write(jsonIn);
            bufferedReader.close();

            Library lib = new Library(tempFileRead.getAbsolutePath());
	    File tempFileWrite = File.createTempFile("libTest4.json", ".tmp");
	    lib.saveLibraryToFile(tempFileWrite.getAbsolutePath());

	    jsonOut = new Scanner(new File(tempFileWrite.getAbsolutePath())).useDelimiter("\\A").next();
	    
            tempFileRead.deleteOnExit();
	    tempFileWrite.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    	assertEquals(jsonIn, jsonOut);
    }

    @Test
    public void testSaveLibraryToFile2() {
        String jsonIn  = "{\"collections\":[{\"elements\":[{\"elements\":[{\"title\":\"MyBook1\",\"author\":\"Author1\"}],\"name\":\"MyCol2\"},{\"title\":\"MyBook2\",\"author\":\"Author1, Author2\"}],\"name\":\"MyCol1\"},{\"elements\":[{\"title\":\"MyBook4\",\"author\":\"Author7, Author8\"}],\"name\":\"MyCol4\"}]}";
        String jsonOut = "";
        try {
            File tempFileRead = File.createTempFile("libTest5.json", ".tmp");
            BufferedWriter bufferedReader = new BufferedWriter(new FileWriter(tempFileRead));

            bufferedReader.write(jsonIn);
            bufferedReader.close();

            Library lib = new Library(tempFileRead.getAbsolutePath());
            File tempFileWrite = File.createTempFile("libTest6.json", ".tmp");
            lib.saveLibraryToFile(tempFileWrite.getAbsolutePath());

            jsonOut = new Scanner(new File(tempFileWrite.getAbsolutePath())).useDelimiter("\\A").next();

            tempFileRead.deleteOnExit();
            tempFileWrite.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
	System.out.println("Expecting: ");
	System.out.println(jsonIn);

	System.out.println("Got: ");
	System.out.println(jsonOut);
        assertEquals(jsonIn, jsonOut);
    }

    @Test
    public void testSaveLibraryToFile3() {
	String jsonOut = "";
	Library lib = new Library();
        try {
            File tempFileWrite = File.createTempFile("libTest6.json", ".tmp");

            lib.saveLibraryToFile(tempFileWrite.getAbsolutePath());
            jsonOut = new Scanner(new File(tempFileWrite.getAbsolutePath())).useDelimiter("\\A").next();

	    tempFileWrite.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("{\"collections\":[]}", jsonOut);
    }

    @Test
    public void testGetCollections1() {
	int s = 0;
        try {
            File tempFile = File.createTempFile("libTest7.json", ".tmp");
            System.out.println("Temp File Name : " + tempFile.getAbsolutePath());
	    
            BufferedWriter bufferedReader = new BufferedWriter(new FileWriter(tempFile));

            bufferedReader.write("{\"collections\":[{\"elements\":[{\"elements\":[{\"title\":\"MyBook1\",\"author\":\"Author1\"}],\"name\":\"MyCol2\"},{\"title\":\"MyBook2\",\"author\":\"Author1, Author2\"}],\"name\":\"MyCol1\"},{\"elements\":[{\"title\":\"MyBook4\",\"author\":\"Author7, Author8\"}],\"name\":\"MyCol4\"}]}");
            bufferedReader.close();

            Library lib = new Library(tempFile.getAbsolutePath());

            tempFile.deleteOnExit();

            List<Collection> col = lib.getCollections();
	    s = col.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(2, s);
    }

}
