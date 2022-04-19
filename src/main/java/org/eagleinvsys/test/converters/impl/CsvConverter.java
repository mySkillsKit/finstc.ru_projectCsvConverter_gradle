package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.Converter;
import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.ConvertibleMessage;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CsvConverter implements Converter {

    /**
     * Converts given {@link ConvertibleCollection} to CSV and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert to CSV format
     * @param outputStream        output stream to write CSV conversion result as text to
     */
    @Override
    public void convert(ConvertibleCollection collectionToConvert, OutputStream outputStream) {
        // TODO: implement

        String path = "/Users/avas/IdeaProjects/finstc.ru_project/src/main/resources/fileCSV.csv";

        try (FileOutputStream writer = new FileOutputStream(path)) {
            for (String string : collectionToConvert.getHeaders()) {
                byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
                writer.write(bytes);
                bytes = (",").getBytes(StandardCharsets.UTF_8);
                writer.write(bytes);
            }
            byte[] bytes = ("\r\n").getBytes(StandardCharsets.UTF_8);
            writer.write(bytes);

            for (ConvertibleMessage msg :
                    collectionToConvert.getRecords()) {
                for (String key : ((Message) msg).keySet()
                ) {
                    bytes = (msg.getElement(key)).getBytes(StandardCharsets.UTF_8);
                    writer.write(bytes);
                    bytes = (",").getBytes(StandardCharsets.UTF_8);
                    writer.write(bytes);
                }
                bytes = ("\r\n").getBytes(StandardCharsets.UTF_8);
                writer.write(bytes);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}