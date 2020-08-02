package undo.unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import undo.Document;
import undo.impl.StringDocument;

public class StringDocumentTest {

    private Document document;

    @Before
    public void init() {
        document = new StringDocument();
    }

    @Test
    public void testDocumentInsert() {
        // prep
        var testString = "test";

        // test
        this.document.insert(0, testString);

        // assert
        assertEquals(this.document.toString(), testString);
    }

    @Test
    public void testMultipleDocumentInsert() {
        // prep
        var testString = "test";

        // test
        this.document.insert(0, testString);
        this.document.insert(testString.length(), testString);

        // assert
        assertEquals(this.document.toString(), testString + testString);
    }

    @Test
    public void testDocumentPaddedInsert() {
        // prep
        var testString = "test";

        // test
        this.document.insert(2, testString);

        // assert
        assertEquals(this.document.toString(), "  " + testString);
    }

    @Test
    public void testMultiplePaddedDocumentInsert() {
        // prep
        var testString = "test";

        // test
        this.document.insert(0, testString);
        this.document.insert(testString.length() + 1, testString);

        // assert
        assertEquals(this.document.toString(), testString + " " + testString);
    }


    @Test(expected = IllegalStateException.class)
    public void testIllegalDocumentInsert() {
        // prep
        var testString = "test";

        // test
        this.document.insert(-1, testString);
    }


    @Test
    public void testDocumentDelete() {
        // prep
        var testString = "test";
        this.document.insert(0, testString);

        // test
        this.document.delete(0, testString);

        // assert
        assertEquals(this.document.toString(), "");
    }

    @Test(expected = IllegalStateException.class)
    public void testDocumentDeleteIllegalPosition() {
        // prep
        var testString = "test";
        this.document.insert(0, testString);

        // test
        this.document.delete(-1, testString);
    }

    @Test(expected = IllegalStateException.class)
    public void testDocumentDeleteIllegalString() {
        // prep
        var testString = "test";
        this.document.insert(0, testString);

        // test
        this.document.delete(0, "random");
    }

    public void testSetDot() {
        // prep
        var testString = "test";
        this.document.insert(0, testString);

        // test
        this.document.setDot(2);
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalSetDot() {
        // prep
        var testString = "test";
        this.document.insert(0, testString);

        // test
        this.document.setDot(testString.length() + 1);
    }

}
