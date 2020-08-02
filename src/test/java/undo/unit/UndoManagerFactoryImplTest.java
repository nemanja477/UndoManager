package undo.unit;

import org.junit.Before;
import org.junit.Test;
import undo.Document;
import undo.impl.StringDocument;
import undo.impl.UndoManagerFactoryImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class UndoManagerFactoryImplTest {

	private Document document;

	@Before
	public void init() {
		this.document = new StringDocument();
	}

	@Test
	public void testCreateUndoManager() {
		// prep
		var undoManagerFactory = new UndoManagerFactoryImpl();

		// test
		var undoManager = undoManagerFactory.createUndoManager(this.document, 10);

		// assert
		assertNotNull(undoManager);
		assertFalse(undoManager.canUndo());
		assertFalse(undoManager.canRedo());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateUndoManagerWithoutDocument() {
		// prep
		var undoManagerFactory = new UndoManagerFactoryImpl();

		// test
		assertNotNull(undoManagerFactory.createUndoManager(null, 10));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateUndoManagerIllegalBufferSize() {
		// prep
		var undoManagerFactory = new UndoManagerFactoryImpl();

		// test
		undoManagerFactory.createUndoManager(this.document, -10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateUndoManagerIllegalParameters() {
		// prep
		var undoManagerFactory = new UndoManagerFactoryImpl();

		// test
		undoManagerFactory.createUndoManager(null, -10);
	}


}
