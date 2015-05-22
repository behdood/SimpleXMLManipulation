package com.me.model.dao.Utils;


import org.xml.sax.InputSource;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.StringWriter;

public class StringXmlDocumentRWUtils extends XmlDocumentRWUtils {

    @Override
    protected InputStream prepareSource() {
        // todo
        throw new RuntimeException("Not Implemented yet");
//        return null;
    }

    @Override
    protected Result prepareResult() {
        // todo
        throw new RuntimeException("Not Implemented yet");
//        return new StreamResult(new StringWriter());
    }
}
