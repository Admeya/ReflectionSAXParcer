package com.innopolis.admeya.datamanagement;

import com.innopolis.admeya.dicts.StructureObject;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import static com.innopolis.admeya.dicts.StructureObject.*;

/**
 * Created by Ирина on 11.02.2017.
 */
public class SerializationManager {
    public static ArrayList<HashMap<String, String>> arList;

    public void visit(Node node) {
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node childNode = list.item(i); // текущий нод
            if (!childNode.toString().contains("text:")) {
                process(childNode); // обработка
            }
            visit(childNode); // рекурсия
        }
    }

    public void process(Node node) {
        String type;
        String val;

        System.out.println(node.getNodeName());
        NamedNodeMap attr = node.getAttributes();
        for (int i = 0; i < attr.getLength(); i++) {
            type = attr.item(i).getNodeName();
            val = attr.item(i).getNodeValue();
            System.out.println(" " + type + " = '" + val + "'");
            identification(type, val);
        }

        if (node instanceof Element) {
            Element e = (Element) node; // работаем как с элементом (у него есть атрибуты и схема)
        }
    }

    public void identification(String fieldName, String fieldValue) {
        HashMap<String, String> map = new HashMap<>();

        switch (fieldName) {
            case ATTR_ID:
                map.put(ATTR_ID, fieldValue);
            case ATTR_TYPE:
                map.put(ATTR_TYPE, fieldValue);
            case ATTR_VALUE:
                map.put(ATTR_VALUE, fieldValue);
        }
        arList.add(map);
    }

    public void serialization(String xmlPath, Object obj) throws ParserConfigurationException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation impl = builder.getDOMImplementation();

        Class people = obj.getClass();
        Document doc = impl.createDocument(null, null, null); // doctype
        Element e1 = doc.createElement(StructureObject.ELEM_OBJECT);
        e1.setAttribute(StructureObject.ATTR_TYPE, people.getSimpleName());
        doc.appendChild(e1);

        Field[] fields = people.getDeclaredFields();
        for (Field field : fields) {
            Element e2 = doc.createElement(StructureObject.ELEM_FIELD);
            field.setAccessible(true);
            e2.setAttribute(StructureObject.ATTR_TYPE, field.getType().getSimpleName());
            e2.setAttribute(StructureObject.ATTR_ID, field.getName());
            e2.setAttribute(StructureObject.ATTR_VALUE, field.get(obj).toString());
            e1.appendChild(e2);
        }
        writeObjectToXML(doc, xmlPath);
    }

    void writeObjectToXML(Document doc, String xmlPath) {
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(System.out);
        TransformerFactory transFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);

            transformer.transform(source, new StreamResult(new FileOutputStream(xmlPath)));
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}


