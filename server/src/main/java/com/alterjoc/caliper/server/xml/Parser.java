/*
 * Copyright 2011-2013, KC CLASS; Matej Lazar, Ales Justin.
 */

package com.alterjoc.caliper.server.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class Parser {

    /**
     * Parse web.xml input stream.
     *
     * @param inputStream the input stream
     * @return parsed dom document
     * @throws IOException for any I/O error
     */
    public static WebXmlMetaData parseWebXml(InputStream inputStream) throws IOException {
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            WebXmlContentHandler contentHandler = new WebXmlContentHandler();
            reader.setContentHandler(contentHandler);
            reader.parse(new InputSource(inputStream));
            return contentHandler.metaData;
        } catch (SAXException e) {
            throw new IOException(e);
        } finally {
            inputStream.close();
        }
    }

    private static class WebXmlContentHandler implements ContentHandler {

        private WebXmlMetaData metaData = new WebXmlMetaData();

        private boolean useChars;
        private String currentKey;
        private Map<String, String> currentMap;

        public void setDocumentLocator(Locator locator) {
        }

        public void startDocument() throws SAXException {
        }

        public void endDocument() throws SAXException {
        }

        public void startPrefixMapping(String prefix, String uri) throws SAXException {
        }

        public void endPrefixMapping(String prefix) throws SAXException {
        }

        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
            useChars = qName.equals("servlet-name");

            if (qName.equals("servlet-class")) {
                currentMap = metaData.servlets;
            } else if (qName.equals("url-pattern")) {
                currentMap = metaData.mappings;
            } else {
                currentMap = null;
            }
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
        }

        public void characters(char[] ch, int start, int length) throws SAXException {
            String value = new String(ch, start, length);
            if (currentMap != null) {
                currentMap.put(currentKey, value);
                currentMap = null;
            } else if (useChars) {
                currentKey = value;
                useChars = false;
            }
        }

        public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        }

        public void processingInstruction(String target, String data) throws SAXException {
        }

        public void skippedEntity(String name) throws SAXException {
        }
    }
}
