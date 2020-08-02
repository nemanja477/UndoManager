package undo.impl;

import undo.Change;
import undo.Document;
import undo.UndoManager;
import undo.UndoManagerFactory;

/**
 * A factory implementation for {@link UndoManager}s.
 *
 * @author Nemanja
 */
public class UndoManagerFactoryImpl implements UndoManagerFactory {

    /**
     * Creates an undo manager for a {@link Document}.
     *
     * @param doc The document to create the {@link UndoManager} for.
     * @param bufferSize The number of {@link Change}es stored.
     * @return The {@link UndoManager} created.
     */
    @Override
    public UndoManager createUndoManager(Document doc, int bufferSize) {
        if(doc == null || bufferSize <= 0) {
            throw new IllegalArgumentException();
        }
        return new UndoManagerImpl(doc, bufferSize);
    }

}
