package undo.impl;

import undo.Document;
import undo.UndoManager;

/**
 * An implementation of a document to be used with the {@link UndoManager}.
 *
 * @author Nemanja
 */
public class StringDocument implements Document {

    /**
     * Default document size.
     */
    private static final int DEFAULT_TEXT_SIZE = 10000;

    /**
     * Buffer where document is stored.
     */
    private final char[] text;

    /**
     * Current dot position at the document.
     */
    private int currentDot;

    /**
     * Points to end of document.
     */
    private int endCursor;

    /**
     * Default constructor.
     */
    public StringDocument() {
        this(DEFAULT_TEXT_SIZE);
    }

    /**
     * Constructor with given size.
     *
     * @param documentSize Size of the created document
     */
    public StringDocument(int documentSize) {
        this.text = new char[documentSize];
        for (int i = 0; i < documentSize; i++) {
            this.text[i] = ' ';
        }
        endCursor = 0;
    }

    /**
     * Deletes a string from the document.
     *
     * @param pos The position to start deletion.
     * @param s The string to delete.
     * @throws IllegalStateException If the document doesn't have <code>s</code>
     * 			as <code>pos</code>.
     */
    @Override
    public void delete(int pos, String s) {
        if(pos < 0 || pos + s.length() > this.text.length) {
            throw new IllegalStateException();
        }
        for (int i = pos, j = 0; i < pos + s.length(); i++, j++) {
            if(this.text[i] != s.charAt(j)) {
                throw new IllegalStateException();
            }
        }
        for(int i = pos; i < pos + s.length(); i++) {
            this.text[i] = ' ';
        }
        if(pos + s.length() > endCursor) {
            endCursor = pos;
        }
    }

    /**
     * Inserts a string into the document.
     *
     * @param pos The position to insert the string at.
     * @param s The string to insert.
     * @throws IllegalStateException If <code>pos</code> is an illegal position
     * 			(that is, if document is shorter than that).
     */
    @Override
    public void insert(int pos, String s) {
        if(pos < 0 || pos + s.length() > DEFAULT_TEXT_SIZE) {
            throw new IllegalStateException();
        }
        for(int i = pos, j = 0; i < pos + s.length(); i++, j++) {
            this.text[i] = s.charAt(j);
        }
        this.currentDot = pos + s.length();
        if(this.currentDot > endCursor) {
            endCursor = this.currentDot;
        }
    }

    /**
     * Sets the dot (cursor) position of the document.
     *
     * @param pos The dot position to set.
     * @throws IllegalStateException If <code>pos</code> is an illegal position
     * 			(that is, if document is shorter than that).
     */
    @Override
    public void setDot(int pos) {
        if(pos > this.text.length || pos > endCursor) {
            throw new IllegalStateException();
        }
        this.currentDot = pos;
    }

    /**
     * Gets current position of the dot.
     *
     * @return Position of the dot
     */
    public int getDot() {
        return this.currentDot;
    }

    /**
     * Creates string from document.
     *
     * @return String representation of the document
     */
    @Override
    public String toString() {
        return String.valueOf(this.text).stripTrailing();
    }

}
