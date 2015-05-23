package com.me.model.exceptions.io;


public class XmlDocumentWriteException extends XmlDocumentIOException {
    public XmlDocumentWriteException() {
        super("Unable to write to xml document.");
    }

    public XmlDocumentWriteException(String message) {
        super(message);
    }
}
