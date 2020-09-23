package com.palehorsestudios.alone.util.reader;

import com.palehorsestudios.alone.GameAssets;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.dayencounter.DayEncounter;
import com.palehorsestudios.alone.dayencounter.Encounter;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class EncounterReader {
  static final String ENCOUNTER = "encounter";
  static final String ENCOUNTER_NAME = "encounterName";
  static final String ENCOUNTER_SOUND = "encounterSound";
  static final String PROTECTIVE_ITEM = "protectiveItem";
  static final String WEIGHT_CHANGE_GOOD = "weightChangeGood";
  static final String MORALE_CHANGE_GOOD = "moraleChangeGood";
  static final String HYDRATION_CHANGE_GOOD = "hydrationChangeGood";
  static final String SHELTER_CHANGE_GOOD = "shelterChangeGood";
  static final String WEIGHT_CHANGE_BAD = "weightChangeBad";
  static final String MORALE_CHANGE_BAD = "moraleChangeBad";
  static final String HYDRATION_CHANGE_BAD = "hydrationChangeBad";
  static final String SHELTER_CHANGE_BAD = "shelterChangeBad";
  static final String RESPONSE_GOOD = "responseGood";
  static final String RESPONSE_BAD = "responseBad";


  @SuppressWarnings( {"null"})
  public static HashMap<String, DayEncounter> readEncountersXML(String itemsFile) {
    HashMap<String, DayEncounter> encounters = new HashMap<>();

    try {
      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
      InputStream in = new FileInputStream(itemsFile);
      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
      Encounter encounter = null;

      while  (eventReader.hasNext()) {
        XMLEvent event = eventReader.nextEvent();

        if (event.isStartElement()) {
          StartElement startElement = event.asStartElement();
          String elementName = startElement.getName().getLocalPart();
//          System.out.println("Element name: " + elementName);
          switch(elementName){
            // if element is encounter root tag make new instance
            case ENCOUNTER ->{
              encounter = new Encounter();
            }
            // if element is an attribute of an encounter, set it to the encounter instance
            case ENCOUNTER_NAME ->{
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setName(event.asCharacters().getData());
              }else{
                System.out.println("EncounterReader error");
              }
            }
            case PROTECTIVE_ITEM -> {
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setProtectiveItem(event.asCharacters().getData());
              }else{
                System.out.println("EncounterReader error");
              }
            }
            case WEIGHT_CHANGE_GOOD ->{
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setWeightChangeGood(Integer.parseInt(event.asCharacters().getData()));
              }else{
                System.out.println("EncounterReader error");
              }
            }
            case MORALE_CHANGE_GOOD->{
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setMoraleChangeGood(Integer.parseInt(event.asCharacters().getData()));
              }else{
                System.out.println("EncounterReader error");
              }
            }
            case HYDRATION_CHANGE_GOOD->{
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setHydrationChangeGood(Integer.parseInt(event.asCharacters().getData()));
              }else{
                System.out.println("EncounterReader error");
              }
            }
            case SHELTER_CHANGE_GOOD->{
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setShelterChangeGood(Integer.parseInt(event.asCharacters().getData()));
              }else{
                System.out.println("EncounterReader error");
              }
            }
            case WEIGHT_CHANGE_BAD ->{
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setWeightChangeBad(Integer.parseInt(event.asCharacters().getData()));
              }else{
                System.out.println("EncounterReader error");
              }
            }
            case MORALE_CHANGE_BAD->{
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setMoraleChangeBad(Integer.parseInt(event.asCharacters().getData()));
              }else{
                System.out.println("EncounterReader error");
              }
            }
            case HYDRATION_CHANGE_BAD->{
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setHydrationChangeBad(Integer.parseInt(event.asCharacters().getData()));
              }else{
                System.out.println("EncounterReader error");
              }
            }
            case SHELTER_CHANGE_BAD->{
              event = eventReader.nextEvent();
              if(encounter != null){
                encounter.setShelterChangeBad(Integer.parseInt(event.asCharacters().getData()));
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

    return encounters;
  }
}
