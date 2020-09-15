package com.palehorsestudios.alone.util.reader;

import com.palehorsestudios.alone.GameAssets;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.dayencounter.WeatherEncounter;

import javax.xml.namespace.QName;
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

public class EncounterReader {
  static final String ENCOUNTER = "encounter";
  static final String ENCOUNTER_NAME = "name";
  static final String PROTECTIVE_ITEM = "protectiveItem";
  static final String WEIGHT_CHANGE = "weightChange";
  static final String MORALE_CHANGE = "moraleChange";
  static final String HYDRATION_CHANGE = "hydrationChange";
  static final String RESPONSE_GOOD = "responseGood";
  static final String RESPONSE_BAD = "responseBad";

  @SuppressWarnings( {"null"})
  public static HashMap<String, WeatherEncounter> readEncountersXML(String itemsFile, HashMap<String, Item> gameItems) {
    HashMap<String, WeatherEncounter> encounters = new HashMap<>();

    try {
      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
      InputStream in = new FileInputStream(itemsFile);
      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
      WeatherEncounter encounter = null;

      while  (eventReader.hasNext()) {
        XMLEvent event = eventReader.nextEvent();

        if (event.isStartElement()) {
          StartElement startElement = event.asStartElement();
          String elementName = startElement.getName().getLocalPart();
//          System.out.println("Element name: " + elementName);
          if (ENCOUNTER.equals(elementName)){
            encounter = new WeatherEncounter();
          }
        }

        if (event.isEndElement()) {
          EndElement endElement = event.asEndElement();
          if (endElement.getName().getLocalPart().equals(ENCOUNTER)) {
            if (encounter != null) {
              encounters.put(encounter.getName(), encounter);
            } else {
              System.out.println("Could not print read encounter xml");
              System.exit(-1);
            }
          }
        }
      }
    } catch (FileNotFoundException | XMLStreamException e) {
      e.printStackTrace();
    }

    for (WeatherEncounter x : encounters.values()) {
      System.out.println(x.getResponseBad());
    }

    return encounters;
  }
}
