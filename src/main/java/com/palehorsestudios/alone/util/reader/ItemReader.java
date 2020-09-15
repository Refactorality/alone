package com.palehorsestudios.alone.util.reader;

import com.palehorsestudios.alone.Food;
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
    static final String NAME = "name";
    static final String ITEM_NAME = "itemName";

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

                    switch (elementName) {
                        case ITEM -> {
                            item = new Item();
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute attribute = attributes.next();
                                if (attribute.getName().toString().equals(NAME)) {
                                    System.out.println(NAME + ": " + attribute.getValue());
                                    item.setName(attribute.getValue());
                                }
                            }
                        }
                        case ITEM_NAME -> {
                            event = eventReader.nextEvent();
                            if (item != null) {
                                item.setItemName(event.asCharacters().getData());
                                System.out.println(item.getItemName());
                            } else {
                                System.out.println("Food not initialized, check foods.xml for error");
                                System.exit(-1);
                            }
                        }
                    }
                }

                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(ITEM)) {
                        if (item != null) {
                            items.put(item.getName(), item);
                        } else {
                            System.out.println("Food not initialized, check food.xml for error");
                            System.exit(-1);
                        }
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return items;
    }
}
