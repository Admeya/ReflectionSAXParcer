package com.innopolis.admeya;

import com.innopolis.admeya.datamanagement.SerializationManager;
import com.innopolis.admeya.entities.People;


public class Main {
    public static void main(String[] args) {
        String xmlPath = "resources/sourceFile.xml";

        People people = new People("John", 20, 20000.);
        SerializationManager helper = new SerializationManager();
        helper.serialization(xmlPath, people);
        helper.deserialization(xmlPath);
    }


}




