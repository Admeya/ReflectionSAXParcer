package com.innopolis;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
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
    private int characters;
    private int ignorableWhitespace;
    private String xmlForParse;

    public SaxParser(String xmlForParse) {
        this.xmlForParse = xmlForParse;
        try {
            out = new PrintWriter(new OutputStreamWriter(System.out, "koi8-r"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e); // нет вывода - нет работы!
        }
    }

    public void startDocument() {
        // Статистика
        elements = 0;
        attributes = 0;
        characters = 0;
        ignorableWhitespace = 0;
        // Процессорные инструкции
        out.println("");
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
    }

    // Текстовые символы
    public void characters(char ch[], int start, int length) {
        characters += length;
        out.println(new String(ch, start, length));
        System.out.println(new String(ch, start, length));
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
        System.out.println("Символов  : " + characters);
    }

}
