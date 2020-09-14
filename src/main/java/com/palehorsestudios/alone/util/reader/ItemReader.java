package com.palehorsestudios.alone.util.reader;

import com.palehorsestudios.alone.Item;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

public class ItemReader {
    static final String ITEM = "item";
    static final String ITEM_NAME = "name";

    @SuppressWarnings( {"null"})
    public static HashMap<String, Item> readItemsXML(String itemsFile) {
        HashMap<String, Item> items = new HashMap<>();

        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(itemsFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            Item item = null;

            while  (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String elementName = startElement.getName().getLocalPart();
                    System.out.println("Element name: " + elementName);

                    if (ITEM.equals(elementName)) {
                        item = new Item();
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(ITEM_NAME)) {
                                System.out.println(attribute.getValue());
                                item.setItemName(attribute.getValue());
                            }
                        }
                    }
                }

                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(ITEM)) {
                        if (item != null) {
                            items.put(item.getItemName(), item);
                        } else {
                            System.out.println("Room not initialized, check rooms.xml for error");
                            System.exit(-1);
                        }
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }

        for (Item x : items.values()) {
            System.out.println(x.getItemName());
        }

        return items;
    }
}
