package me.toidicakhia.xmlparser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

public class XMLParser {
    public static XMLElement parse(String text) throws Exception {
        return parse(new ByteArrayInputStream(text.getBytes()));
    }

    public static XMLElement parse(InputStream inputStream) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        return new XMLElement(doc.getDocumentElement());
    }

    public static XMLElement parse(File file) throws Exception {
        return parse(Files.newInputStream(file.toPath()));
    }

    public static XMLElement newXML(String rootElementName) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        // Create and set the root element
        Element rootElement = doc.createElement(rootElementName);
        doc.appendChild(rootElement);

        return new XMLElement(rootElement);
    }
}