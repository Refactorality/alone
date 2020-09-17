package com.palehorsestudios.alone.util.reader;

import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.GameAssets;
import com.palehorsestudios.alone.Item;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

public class ResourcesRequiredReader {
    static final String CONSTRUCTED_ITEM = "constructedItem";
    static final String TYPE = "type";
    static final String ITEM = "item";
    static final String FOOD = "food";
    static final String NAME = "name";
    static final String RESOURCE = "resource";

    @SuppressWarnings( {"null"})
    public static void readRequiredResourcesXML(String requiredResourcesFile) {
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(requiredResourcesFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            Item item = null;
            Food food = null;
            String type = "";

            while  (eventReader.hasNext()) {

                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String elementName = startElement.getName().getLocalPart();

                    switch (elementName) {
                        case CONSTRUCTED_ITEM -> {
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute attribute = attributes.next();
                                String itemName = attribute.getValue();

                                attribute = attributes.next();
                                type = attribute.getValue();

                                if (type.equals(ITEM) && GameAssets.gameItems.containsKey(itemName)) {
                                    item = GameAssets.gameItems.get(itemName);
                                }
                                else if (type.equals(FOOD) && GameAssets.gameFoods.containsKey(itemName)) {
                                    food = GameAssets.gameFoods.get(itemName);
                                }
                                else {
                                    System.out.println("Check requiredResources.xml for misspelled <constructedItem type='"+itemName+"'");
                                    System.exit(-1);
                                }
                            }
                        }

                        case RESOURCE -> {
                            event = eventReader.nextEvent();

                            String resourceItem = event.asCharacters().getData();

                            if (type.equals(ITEM) && GameAssets.gameItems.containsKey(resourceItem)) {
                                item.addResourceRequired(GameAssets.gameItems.get(resourceItem));
                            } else if (type.equals(FOOD) && GameAssets.gameFoods.containsKey(resourceItem)) {
                                food.addResourceRequired(GameAssets.gameFoods.get(resourceItem));
                            } else {
                                System.out.println("Check resourcesRequired.xml for misspelled <resource>" + resourceItem);
                                System.exit(-1);
                            }

                        }
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
