package org.eagleinvsys.test.converters;

import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.eagleinvsys.test.converters.impl.Message;
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

class CsvConverterTests {

    // TODO: implement JUnit 5 tests for CsvConverter
    CsvConverter sut;
    private ConvertibleCollection collection;
    private Map<String, String> map;
    private List<Map<String, String>> collectionToConvert;
    private Collection<String> headers;

    @BeforeEach
    public void init() {

        sut = new CsvConverter();

        map = new HashMap<>();
        map.put("header1", "message1");
        map.put("header2", "message2");
        map.put("header3", "message3");
        map.put("header4", "message4");
        collectionToConvert = new ArrayList<>();
        collectionToConvert.add(map);

        headers = collectionToConvert.stream()
                .flatMap(map -> map.keySet().stream())
                .distinct()
                .collect(Collectors.toList());

        collection = new ConvertibleCollection() {

            @Override
            public Collection<String> getHeaders() {
                Set<String> headers = new HashSet<>();
                for (ConvertibleMessage msg :
                        this.getRecords()) {
                    for (String key : ((Message) msg).keySet()
                    ) {
                        headers.add(key);
                    }
                }
                return headers;
            }

            @Override
            public Iterable<ConvertibleMessage> getRecords() {
                List<ConvertibleMessage> list = new ArrayList<>();
                list.add(new Message(map));
                return list;
            }
        };
    }

    @BeforeAll
    public static void startedAll() {
        System.out.println("CsvConverterTests started");

    }

    @Test
    public void testConvert() {
        // given:
        OutputStream outputStream = null;

        // when:
        sut.convert(collection, outputStream);

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
        sut.convert(collection, outputStream);

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
