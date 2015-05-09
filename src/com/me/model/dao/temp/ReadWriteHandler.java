package com.me.model.dao.temp;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ReadWriteHandler implements IReadWriteHandler {

    private DocumentBuilderFactory dbf;

    public ReadWriteHandler() throws ParserConfigurationException {
        dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(false);
//        dbf.setNamespaceAware(true);

    }

    @Override
    public Node readStreamToNode(InputStream is) {
        return null;
    }

    @Override
    public void writeNodeToStream(Node node, OutputStream os) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(node);

        StreamResult streamResult = new StreamResult(os);

        transformer.transform(source, streamResult);
    }

    @Override
    public Document readStreamToDocument(InputStream is) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
        return documentBuilder.parse(is);
    }

    @Override
    public void writeDocumentToStream(Document doc, OutputStream os) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
//        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
//        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
        DOMSource source = new DOMSource(doc);

        StreamResult streamResult = new StreamResult(os);

        transformer.transform(source, streamResult);
    }


}
