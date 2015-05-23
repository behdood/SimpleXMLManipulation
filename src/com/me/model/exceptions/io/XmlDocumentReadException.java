package com.me.model.exceptions.io;


public class XmlDocumentReadException extends XmlDocumentIOException {
    public XmlDocumentReadException() {
        super("Unable to read from xml document.");
    }

    public XmlDocumentReadException(String message) {
        super(message);
    }
}
