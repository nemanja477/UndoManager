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
    private final StringBuffer text;

    /**
     * Current dot position at the document.
     */
    private int currentDot;

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
        this.text = new StringBuffer(documentSize);
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
        if(pos < 0 || pos + s.length() > this.text.capacity()) {
            throw new IllegalStateException();
        }
        if(!this.text.substring(pos, pos + s.length()).equals(s)) {
            throw new IllegalStateException();
        }
        this.text.delete(pos, pos + s.length());
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
        // Process case when starting position is larger.
        if(pos > text.length()) {
            s = this.padLeft(s, pos - this.text.length() + s.length());
            pos -= pos - this.text.length();
        }
        this.text.replace(pos, pos + s.length(), s);
        this.currentDot = pos + s.length();
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
        if(pos > this.text.length()) {
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
        return this.text.toString();
    }

    /**
     * Pad the given string with empty spaces.
     *
     * @param inputString String to be padded
     * @param length Size up to which string should be padded.
     * @return Padded string
     */
    private String padLeft(String inputString, int length) {
        return String.format("%1$" + length + "s", inputString);
    }

}
