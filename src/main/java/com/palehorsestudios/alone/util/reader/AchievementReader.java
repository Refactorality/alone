package com.palehorsestudios.alone.util.reader;

import com.palehorsestudios.alone.Achievement;

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

public class AchievementReader {
    static final String ACHIEVEMENT = "achievement";
    static final String NAME = "name";
    static final String ACHIEVEMENT_NAME = "achievementName";

    @SuppressWarnings( {"null"})
    public static HashMap<String, Achievement> readAchievementsXML() {
        HashMap<String, Achievement> achievements = new HashMap<>();
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream("resources/xml/achievements.xml");
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            Achievement achievement = null;

            while  (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String elementName = startElement.getName().getLocalPart();

                    switch (elementName) {
                        case ACHIEVEMENT -> {
                            achievement = new Achievement();
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute attribute = attributes.next();
                                if (attribute.getName().toString().equals(NAME)) {
                                    achievement.setName(attribute.getValue());
                                }
                            }
                        }
                        case ACHIEVEMENT_NAME -> {
                            event = eventReader.nextEvent();
                            if (achievement != null) {
                                achievement.setVisibleName(event.asCharacters().getData());
                            } else {
                                System.out.println("Achievement not initialized, check achievement.xml for error");
                                System.exit(-1);
                            }
                        }
                    }
                }

                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(ACHIEVEMENT)) {
                        if (achievement != null) {
                            achievements.put(achievement.getName(), achievement);
                        } else {
                            System.out.println("Achievement not initialized, check achievement.xml for error");
                            System.exit(-1);
                        }
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return achievements;
    }
}
