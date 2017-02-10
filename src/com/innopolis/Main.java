package com.innopolis;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
//	People people = new People();
//        try {
//            people.getClass().getConstructor();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//
//        people.getClass().getDeclaredFields();
//        people.getClass().getMethods();
        SaxParser sample = new SaxParser(args[0]);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(false);

        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(args[0], sample);
            sample.printInfo();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
