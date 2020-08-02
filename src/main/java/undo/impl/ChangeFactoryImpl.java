package undo.impl;

import undo.Change;
import undo.ChangeFactory;
import undo.Document;

/**
 * An implementation that allows creation of a deletion or insertion {@link Change}
 * into a {@link Document}.
 *
 * @author Nemanja
 */
public class ChangeFactoryImpl implements ChangeFactory {

    /**
     * Creates a deletion change.
     *
     * @param pos The position to start the deletion.
     * @param s The string to delete.
     * @param oldDot The dot (cursor) position before the deletion.
     * @param newDot The dot (cursor) position after the deletion.
     * @return The deletion {@link Change}.
     */
    @Override
    public Change createDeletion(int pos, String s, int oldDot, int newDot) {
        return new ChangeImpl(pos, s, oldDot, newDot, ChangeType.DELETE);
    }

    /**
     * Creates an insertion change.
     *
     * @param pos The position at which to insert.
     * @param s The string to insert.
     * @param oldDot The dot (cursor) position before the insertion.
     * @param newDot The dot (cursor) position after the insertion.
     * @return The insertion {@link Change}.
     */
    @Override
    public Change createInsertion(int pos, String s, int oldDot, int newDot) {
        return new ChangeImpl(pos, s, oldDot, newDot, ChangeType.INSERT);
    }

}
