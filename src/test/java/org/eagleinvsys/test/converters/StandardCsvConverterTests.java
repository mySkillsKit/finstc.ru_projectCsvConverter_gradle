package org.eagleinvsys.test.converters;

import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.eagleinvsys.test.converters.impl.StandardCsvConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StandardCsvConverterTests {

    // TODO: implement JUnit 5 tests for StandardCsvConverter
    StandardCsvConverter sut;
    private Map<String, String> map;
    private List<Map<String, String>> collectionToConvert;
    private Collection<String> headers;

    @BeforeEach
    public void init() {

        sut = new StandardCsvConverter(new CsvConverter());

        map = new HashMap<>();
        map.put("header1", "message1");
        map.put("header2", "message2");
        map.put("header3", "message3");
        map.put("header4", "message4");
        collectionToConvert = new ArrayList<>();
        collectionToConvert.add(map);
        map = new HashMap<>();
        map.put("header1", "message5");
        map.put("header2", "message6");
        map.put("header3", "message7");
        map.put("header4", "message8");
        collectionToConvert.add(map);
        map = new HashMap<>();
        map.put("header1", "message9");
        map.put("header2", "message10");
        map.put("header3", "message11");
        map.put("header4", "message12");
        collectionToConvert.add(map);

        /*collectionToConvert.stream()
                .forEach(System.out::println);*/

        headers = collectionToConvert.stream()
                .flatMap(map -> map.keySet().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    @BeforeAll
    public static void startedAll() {
        System.out.println("StandardCsvConverterTests started");

    }

    @Test
    public void testStConvert() {
        // given:
        OutputStream outputStream = null;

        // when:
        sut.convert(collectionToConvert, outputStream);

        // then:
        assertTrue(isFileCreated());

    }

    private boolean isFileCreated() {
        String path = "/Users/avas/IdeaProjects/finstc.ru_project/src/main/resources/fileCSV.csv";

        try (FileInputStream fin = new FileInputStream(path)) {
            int i;
            while ((i = fin.read()) != -1) {
                System.out.print((char) i);
            }
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testCSV.csv")
    void readCSV(String title, String title1, String title2, String title3) {
        headers.add(title);
        headers.add(title1);
        headers.add(title2);
        headers.add(title3);
        Set<String> headerstmp = new HashSet<>();

        for (String key : map.keySet()) {
            for (String header : headers) {
                if (key.equals(header)) {
                    headerstmp.add(header);
                }
            }
        }
        headers = headerstmp;

        // given:
        OutputStream outputStream = null;

        // when:
        sut.convert(collectionToConvert, outputStream);

        boolean result = isHeadersCorrect(headers, map);
        // then:
        assertTrue(result);
    }

    private boolean isHeadersCorrect(Collection<String> headers, Map<String, String> map) {
        for (String key : map.keySet()) {
            for (String header : headers) {
                if (key.equals(header)) {
                    return true;
                }
            }
        }
        return false;
    }
}