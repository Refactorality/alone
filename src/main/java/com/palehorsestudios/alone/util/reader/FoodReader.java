package com.palehorsestudios.alone.util.reader;

import com.palehorsestudios.alone.Food;

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

public class FoodReader {
    static final String FOOD = "food";
    static final String NAME = "name";
    static final String FOOD_NAME = "foodName";
    static final String CALORIES_PER_GRAM = "caloriesPerGram";
    static final String GRAMS = "grams";

    @SuppressWarnings( {"null"})
    public static HashMap<String, Food> readFoodsXML(String foodsFile) {
        HashMap<String, Food> foods = new HashMap<>();
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(foodsFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            Food food = null;

            while  (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String elementName = startElement.getName().getLocalPart();

                    switch (elementName) {
                        case FOOD -> {
                            food = new Food();
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute attribute = attributes.next();
                                if (attribute.getName().toString().equals(NAME)) {
                                    food.setName(attribute.getValue());
                                }
                            }
                        }
                        case FOOD_NAME -> {
                            event = eventReader.nextEvent();
                            if (food != null) {
                                food.setVisibleName(event.asCharacters().getData());
                            } else {
                                System.out.println("Food not initialized, check foods.xml for error");
                                System.exit(-1);
                            }
                        }
                        case CALORIES_PER_GRAM -> {
                            event = eventReader.nextEvent();
                            if (food != null) {
                                food.setCaloriesPerGram(Double.parseDouble(event.asCharacters().getData()));
                            } else {
                                System.out.println("Food not initialized, check foods.xml for error");
                                System.exit(-1);
                            }
                        }
                        case GRAMS -> {
                            event = eventReader.nextEvent();
                            if (food != null) {
                                food.setGrams(Double.parseDouble(event.asCharacters().getData()));
                            } else {
                                System.out.println("Food not initialized, check foods.xml for error");
                                System.exit(-1);
                            }
                        }
                    }
                }

                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(FOOD)) {
                        if (food != null) {
                            foods.put(food.getName(), food);
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
        return foods;
    }
}
