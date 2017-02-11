package com.innopolis.admeya.draft;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by Ирина on 10.02.2017.
 */
public class SaxParser extends DefaultHandler {
    private PrintWriter out;
    private int elements;
    private int attributes;
    private String xmlForParse;

    public SaxParser(String xmlForParse) {
        this.xmlForParse = xmlForParse;
        try {
            out = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e); // нет вывода - нет работы!
        }
    }

    public void startDocument() {
        // Статистика
        elements = 0;
        attributes = 0;
    }

    public void startElement (String uri, String localName,
                              String qName, Attributes attrs){
        this.elements++;
        if (attrs != null) {
            this.attributes += attrs.getLength();
        }

        // Печать тэга элемента вместе со списком его атрибутов,
        // например,
        out.print('<');
        out.print(qName);
        if (attrs != null) {
            int len = attrs.getLength();
            for (int i = 0; i < len; i++) {
                out.print(' ');
                out.print(attrs.getQName(i));
                out.print("=\"");
                out.print(attrs.getValue(i));
                out.print('"');
            }
        }
        out.println('>');

//        if (qName.equals(StructureObject.ELEM_OBJECT)){
//            //if (attrs.getQName())
//        }
    }

    public void serialization(String qName, Attributes attrs) {
        // if (qName.)
    }

    // Конец документа
    public void endDocument() {
        out.flush();
    }

    public void printInfo() {
        System.out.println();
        System.out.println("Документ " + xmlForParse + " был успешно обработан");
        System.out.println("Элементов : " + elements);
        System.out.println("Атрибутов : " + attributes);
    }

}
