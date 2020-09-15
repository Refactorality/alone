package com.palehorsestudios.alone.util.reader;

import com.palehorsestudios.alone.GameAssets;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.dayencounter.DayEncounter;
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
  static final String ENCOUNTER_NAME = "encounterName";
  static final String PROTECTIVE_ITEM = "protectiveItem";
  static final String WEIGHT_CHANGE = "weightChange";
  static final String MORALE_CHANGE = "moraleChange";
  static final String HYDRATION_CHANGE = "hydrationChange";
  static final String RESPONSE_GOOD = "responseGood";
  static final String RESPONSE_BAD = "responseBad";

  @SuppressWarnings( {"null"})
  public static HashMap<String, DayEncounter> readEncountersXML(String itemsFile, HashMap<String, Item> gameItems) {
    HashMap<String, DayEncounter> encounters = new HashMap<>();

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
          switch(elementName){
            case ENCOUNTER ->{
              encounter = new WeatherEncounter();
            }
            case ENCOUNTER_NAME ->{
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setName(event.asCharacters().getData());
              }else{
                System.out.println("EncounterReader error");
              }
            }
            case WEIGHT_CHANGE->{
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setProtectiveItem(GameAssets.getGameItems().get(event.asCharacters().getData()));
              }else{
                System.out.println("EncounterReader error");
              }
            }
            case MORALE_CHANGE->{
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setMoraleChange(Integer.parseInt(event.asCharacters().getData()));
              }else{
                System.out.println("EncounterReader error");
              }
            }
            case HYDRATION_CHANGE->{
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setHydrationChange(Integer.parseInt(event.asCharacters().getData()));
              }else{
                System.out.println("EncounterReader error");
              }
            }
            case RESPONSE_GOOD->{
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setResponseGood(event.asCharacters().getData());
              }else{
                System.out.println("EncounterReader error");
              }
            }case RESPONSE_BAD->{
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setResponseBad(event.asCharacters().getData());
              }else{
                System.out.println("EncounterReader error");
              }
            }
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

    for (DayEncounter x : encounters.values()) {
    }

    return encounters;
  }
}
