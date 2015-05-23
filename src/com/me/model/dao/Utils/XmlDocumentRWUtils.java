package com.me.model.dao.Utils;


import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public abstract class XmlDocumentRWUtils {


//    public Document readDocument(InputStream is) throws ParserConfigurationException, IOException, SAXException {
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        dbf.setNamespaceAware(true);
//        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
//
//        Document doc = documentBuilder.parse(is);
//        System.out.println("NS = " + doc.getNamespaceURI());
//        return doc;
//    }

//    public StreamResult writeDocument(Document document, String filename) throws TransformerException, FileNotFoundException {
//
//        Transformer transformer = prepareTransformer();
//
//        DOMSource domSource = new DOMSource(document);
//
//        StreamResult streamResult = prepareResult(filename);
//
//        transformer.transform(domSource, streamResult);
//
//        return streamResult;
//    }


    public Document loadDocument() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
//        dbf.setNamespaceAware(false);
        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();

        InputStream inputStream = prepareSource();

        return documentBuilder.parse(inputStream);
    }

    public Result saveDocument(Document document) throws TransformerException, FileNotFoundException {
        Transformer transformer = prepareTransformer();

        Source source = new DOMSource(document);

        Result result = prepareResult();

        transformer.transform(source, result);

        return result;
    }


    protected abstract InputStream prepareSource() throws FileNotFoundException;

    protected abstract Result prepareResult() throws FileNotFoundException;


    private static Transformer prepareTransformer() throws TransformerConfigurationException {
        Transformer transformer= TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");
        return transformer;
    }


}
