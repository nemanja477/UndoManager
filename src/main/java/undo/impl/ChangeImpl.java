package undo.impl;

import undo.Change;
import undo.Document;
import undo.UndoManager;


/**
 * An implementation of a change that can be registered with an
 * {@link UndoManager} and applied to a {@link Document}.
 *
 * @author Nemanja
 */
public class ChangeImpl implements Change {

    /**
     * Type of the change
     */
    private final ChangeType type;

    /**
     * The position to start the change.
     */
    private final int pos;

    /**
     * The string to change.
     */
    private final String string;

    /**
     * The dot (cursor) position before the change.
     */
    private final int oldDot;

    /**
     * The dot (cursor) position after the change.
     */
    private final int newDot;

    /**
     * Constructor.
     *
     * @param pos The position to start the change.
     * @param string The string to change.
     * @param oldDot The dot (cursor) position before the change.
     * @param newDot The dot (cursor) position after the change.
     * @return {@link Change}.
     */
    public ChangeImpl(int pos, String string, int oldDot, int newDot, ChangeType type) {
        this.pos = pos;
        this.string = string;
        this.oldDot = oldDot;
        this.newDot = newDot;
        this.type = type;
    }

    /**
     * Gets type of change.
     *
     * @return Type of change
     */
    @Override
    public String getType() {
        return this.type.toString();
    }

    /**
     * Apply this change to the given document.
     *
     * @param doc The document to apply the change to.
     * @throws IllegalStateException If the change cannot be applied to <code>doc</code>
     * 			(that is if the document refuses the application of the change).
     */
    @Override
    public void apply(Document doc) {
        if (this.type == ChangeType.INSERT) {
            doc.insert(this.pos, this.string);
        } else {
            doc.delete(this.pos, this.string);
        }
        doc.setDot(this.newDot);
    }

    /**
     * Reverts this change in the given document.
     *
     * @param doc The document to revert the change in.
     * @throws IllegalStateException If the change cannot be reverted in <code>doc</code>
     * 			(that is if the document refuses the reversion of the change).
     */
    @Override
    public void revert(Document doc) {
        if (this.type == ChangeType.INSERT) {
            doc.delete(this.pos, this.string);
        } else {
            doc.insert(this.pos, this.string);
        }
        doc.setDot(this.oldDot);
    }

}
