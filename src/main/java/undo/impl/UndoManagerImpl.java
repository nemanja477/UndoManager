package undo.impl;

import undo.Change;
import undo.Document;
import undo.UndoManager;

import util.FixedSizeStack;

/**
 * A manager implementation for undo and redo operations to {@link Document}s, based
 * on {@link Change} objects.
 *
 * @author Nemanja
 */
public class UndoManagerImpl implements UndoManager {

    /**
     * Document manager is managing.
     */
    private final Document doc;

    /**
     * Stack for undo actions.
     */
    private final FixedSizeStack<Change> undoStack;

    /**
     * Stack for redo actions.
     */
    private final FixedSizeStack<Change> redoStack;

    /**
     * Constructor.
     *
     * @param doc Document to be managed.
     * @param bufferSize Size of buffer
     */
    public UndoManagerImpl(Document doc, int bufferSize) {
        if (bufferSize < 0 || doc == null) {
            throw new IllegalArgumentException();
        }
        this.doc = doc;
        this.redoStack = new FixedSizeStack<>(bufferSize);
        this.undoStack = new FixedSizeStack<>(bufferSize);
    }

    /**
     * Register change to the manager.
     *
     * @param change The change to register.
     */
    @Override
    public void registerChange(Change change) {
        this.undoStack.push(change);
    }

    /**
     * Check if undo is possible.
     *
     * @return If manager can undo action.
     */
    @Override
    public boolean canUndo() {
        return !undoStack.empty();
    }

    /**
     * Undo latest action.
     */
    @Override
    public void undo() {
        if(!canUndo()) {
            throw new IllegalStateException();
        }
        var change = this.undoStack.pop();
        change.revert(this.doc);
        this.redoStack.push(change);
    }

    /**
     * Check if redo is possible.
     *
     * @return If manager can redo action.
     */
    @Override
    public boolean canRedo() {
        return !redoStack.empty();
    }

    /**
     * Redo latest action.
     */
    @Override
    public void redo() {
        if(!canRedo()) {
            throw new IllegalStateException();
        }
        var change = this.redoStack.pop();
        change.apply(this.doc);
        this.undoStack.push(change);
    }

}
