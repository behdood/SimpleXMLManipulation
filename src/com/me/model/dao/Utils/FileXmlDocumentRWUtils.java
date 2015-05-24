package com.me.model.dao.Utils;


import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class FileXmlDocumentRWUtils extends XmlDocumentRWUtils {


    // usage:
//    InputStream is = new FileInputStream(file);
//    InputStream is = new ByteArrayInputStream(s.getBytes());

//    OutputStream os = new FileOutputStream(file);

    private String filename;

    public FileXmlDocumentRWUtils(String filename) {
        this.filename = filename;
    }

    @Override
    protected InputStream prepareSource() throws FileNotFoundException {
        return new FileInputStream(filename);
    }

    @Override
    protected Result prepareResult() throws FileNotFoundException {
        return new StreamResult(new FileOutputStream(filename));
    }




//    public static Document readFileToDocument(File file) throws IOException, ParserConfigurationException, SAXException {
//        return readStreamToDocument(new FileInputStream(file));
//    }
//
//    public static Document readStringToDocument(String s) throws ParserConfigurationException, SAXException, IOException {
//        return readStreamToDocument(new ByteArrayInputStream(s.getBytes()));
//    }

//    private static Document readStreamToDocument(InputStream is) throws IOException, SAXException, ParserConfigurationException {
//        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//        return documentBuilder.parse(is);
//    }


//    public static String writeDocumentToString(Document document) throws TransformerException {
//
//        Transformer transformer = prepareTransformer();
//
//        DOMSource domSource = new DOMSource(document);
//
//        StreamResult streamResult = new StreamResult(new StringWriter());
//streamResult.
//        transformer.transform(domSource, streamResult);
//        return streamResult.getWriter().toString();
//    }

//    public static String saveDocumentToFile(File file, Document document) throws IOException, TransformerException {
//
//        Transformer transformer = prepareTransformer();
//
//        DOMSource domSource = new DOMSource(document);
//
////        file.createNewFile();
//        StreamResult streamResult = new StreamResult(file);
//
//        transformer.transform(domSource, streamResult);
//        return streamResult.getWriter().toString();
//    }


//    private static Transformer prepareTransformer() throws TransformerConfigurationException {
//        Transformer transformer=TransformerFactory.newInstance().newTransformer();
//        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
//        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
//        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");
//
//        return transformer;
//    }


//    private DocumentBuilderFactory dbf;
//
//    public FileXmlDocumentRWUtils() throws ParserConfigurationException {
//        dbf = DocumentBuilderFactory.newInstance();
//        dbf.setNamespaceAware(false);
//    }
//
//
//    public Node readStreamToNode(InputStream is) {
//        return null;
//    }
//
//
//    public void writeNodeToStream(Node node, OutputStream os) throws TransformerException {
//        Transformer transformer = TransformerFactory.newInstance().newTransformer();
////        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
////        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
//
//        DOMSource source = new DOMSource(node);
//
//        StreamResult streamResult = new StreamResult(os);
//
//        transformer.transform(source, streamResult);
//    }
//
//
//    public Document readStreamToDocument(InputStream is) throws IOException, SAXException, ParserConfigurationException {
////        is.reset();
//        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
//        return documentBuilder.parse(is);
//    }
//
//
//    public void writeDocumentToStream(Document doc, OutputStream os) throws TransformerException {
//        Transformer transformer = TransformerFactory.newInstance().newTransformer();
////        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
////        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
////        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
////        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
////        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
//        DOMSource source = new DOMSource(doc);
//
//        StreamResult streamResult = new StreamResult(os);
//
//        transformer.transform(source, streamResult);
//    }
}
