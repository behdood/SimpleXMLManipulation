package com.me.model.exceptions.storage;


import com.me.model.exceptions.io.XmlDocumentException;

public class MalformedXmlNodeException extends CorruptStorageException {

    public MalformedXmlNodeException() {
        super("XML Node is not well-formed");
    }

    public MalformedXmlNodeException(String message) {
        super(message);
    }

}
