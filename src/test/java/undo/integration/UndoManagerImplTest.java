package undo.integration;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import undo.ChangeFactory;
import undo.Document;
import undo.UndoManagerFactory;
import undo.impl.ChangeFactoryImpl;
import undo.impl.StringDocument;
import undo.impl.UndoManagerFactoryImpl;

public class UndoManagerImplTest {

    private static final int BUFFER_SIZE = 10;

    private Document document;

    private UndoManagerFactory undoFactory;

    private ChangeFactory changeFactory;

    @Before
    public void init() {
        this.document = new StringDocument();
        this.undoFactory = new UndoManagerFactoryImpl();
        this.changeFactory = new ChangeFactoryImpl();
    }

    @Test
    public void testSingleUndo() {
        // prepare
        var testString = "TEST";
        var undoManager = undoFactory.createUndoManager(this.document, BUFFER_SIZE);
        var change = this.changeFactory.createInsertion(0, testString, 0, 4);
        undoManager.registerChange(change);
        this.document.insert(0, testString);

        // test
        undoManager.undo();
        // assert
        assertEquals(this.document.toString(), "");
    }

    @Test
    public void testUndoAndRedo() {
        // prepare
        var testString = "TEST";
        var undoManager = undoFactory.createUndoManager(this.document, BUFFER_SIZE);
        var change = this.changeFactory.createInsertion(0, testString, 0, 4);
        undoManager.registerChange(change);
        this.document.insert(0, testString);

        // test
        undoManager.undo();
        undoManager.redo();
        // assert
        assertEquals(this.document.toString(), testString);
    }

    @Test
    public void testUndoWithMultipleInsertions() {
        // prepare
        var testString = "TEST";
        var undoManager = undoFactory.createUndoManager(this.document, BUFFER_SIZE);
        var change1 = this.changeFactory.createInsertion(0, testString, 0, 0);
        var change2 = this.changeFactory.createInsertion(testString.length(), testString, 0, 0);
        var change3 = this.changeFactory.createInsertion(
                2 * testString.length(), testString, 0, 0
        );
        undoManager.registerChange(change1);
        undoManager.registerChange(change2);
        undoManager.registerChange(change3);
        change1.apply(this.document);
        change2.apply(this.document);
        change3.apply(this.document);

        // test
        assertEquals(this.document.toString(), testString + testString + testString);

        undoManager.undo();
        assertEquals(this.document.toString(), testString + testString);

        undoManager.undo();
        assertEquals(this.document.toString(), testString);

        undoManager.undo();
        assertEquals(this.document.toString(), "");
    }

    @Test
    public void testRedoWithMultipleInsertions() {
        // prepare
        var testString = "TEST";
        var undoManager = undoFactory.createUndoManager(this.document, BUFFER_SIZE);
        var change1 = this.changeFactory.createInsertion(0, testString, 0, 0);
        var change2 = this.changeFactory.createInsertion(testString.length(), testString, 0, 0);
        undoManager.registerChange(change1);
        undoManager.registerChange(change2);
        change1.apply(this.document);
        change2.apply(this.document);
        undoManager.undo();
        undoManager.undo();

        undoManager.redo();
        assertEquals(this.document.toString(), testString);
        undoManager.redo();
        assertEquals(this.document.toString(), testString + testString);
    }

    @Test
    public void testUndoFullBuffer() {
        // prepare
        var testString1 = "TEST1";
        var testString2 = "TEST2";
        var testString3 = "TEST3";
        var undoManager = undoFactory.createUndoManager(this.document, 2);
        var change1 = this.changeFactory.createInsertion(0, testString1, 0, 4);
        var change2 = this.changeFactory.createInsertion(testString1.length(), testString2, 0, 4);
        var change3 = this.changeFactory.createInsertion(testString1.length() + testString2.length(), testString3, 0, 4);
        undoManager.registerChange(change1);
        undoManager.registerChange(change2);
        undoManager.registerChange(change3);
        this.document.insert(0, testString1);
        this.document.insert(testString1.length(), testString2);
        this.document.insert(testString1.length() + testString2.length(), testString3);

        // test
        assertEquals(this.document.toString(), testString1 + testString2 + testString3);
        undoManager.undo();
        assertEquals(this.document.toString(), testString1 + " ".repeat(testString2.length()) + testString3);
        undoManager.undo();
        assertEquals(this.document.toString(), testString1);
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalRedo() {
        // prepare
        var testString = "TEST";
        var undoManager = undoFactory.createUndoManager(this.document, BUFFER_SIZE);
        var change = this.changeFactory.createInsertion(0, testString, 0, 4);
        undoManager.registerChange(change);

        // test
        undoManager.redo();
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalUndo() {
        // prepare
        var undoManager = undoFactory.createUndoManager(this.document, BUFFER_SIZE);
        // test
        undoManager.undo();
    }

    @Test(expected = IllegalStateException.class)
    public void testUndoReplacedString() {
        // prepare
        var testString1 = "TEST1";
        var testString2 = "TEST2";
        var undoManager = undoFactory.createUndoManager(this.document, BUFFER_SIZE);
        var change1 = this.changeFactory.createInsertion(0, testString1, 0, 4);
        var change2 = this.changeFactory.createInsertion(0, testString2, 0, 4);
        undoManager.registerChange(change1);
        undoManager.registerChange(change2);
        this.document.insert(0, testString1);
        this.document.insert(0, testString2);

        // test
        undoManager.undo();
        undoManager.undo();
    }

}
