package com.me.model.dao.tentative;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IReadWriteHandler {

    Node readStreamToNode(InputStream is);

    void writeNodeToStream(Node node, OutputStream os) throws TransformerException;

    Document readStreamToDocument(InputStream is) throws IOException, SAXException, ParserConfigurationException;

    void writeDocumentToStream(Document doc, OutputStream os) throws TransformerException;
}
