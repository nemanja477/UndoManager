package undo.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import undo.Change;
import undo.Document;
import undo.UndoManager;
import undo.impl.UndoManagerImpl;


public class UndoManagerImplTest {

    private static final int BUFFER_SIZE = 10;

    private UndoManager undoManager;

    @Before
    public void init() {
        this.undoManager = new UndoManagerImpl(Mockito.mock(Document.class), BUFFER_SIZE);
    }


    @Test
    public void testRegisterChange() {
        this.undoManager.registerChange(Mockito.mock(Change.class));
    }


    @Test
    public void testCanUndo() {
        // prep
        this.undoManager.registerChange(Mockito.mock(Change.class));

        // test
        this.undoManager.undo();
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalMultipleUndo() {
        // prep
        this.undoManager.registerChange(Mockito.mock(Change.class));
        this.undoManager.undo();

        // test
        this.undoManager.undo();
    }

    public void testCanRedo() {
        // prep
        this.undoManager.registerChange(Mockito.mock(Change.class));
        this.undoManager.undo();

        // test
        this.undoManager.redo();
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalCanUndo() {
        // test
        this.undoManager.undo();
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalCanRedo() {
        // test
        this.undoManager.redo();
    }


}
