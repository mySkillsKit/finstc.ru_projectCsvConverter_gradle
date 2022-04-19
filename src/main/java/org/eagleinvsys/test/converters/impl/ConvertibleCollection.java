package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.ConvertibleMessage;

import java.util.*;

public class ConvertibleCollection extends ArrayList<ConvertibleMessage> implements org.eagleinvsys.test.converters.ConvertibleCollection {


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

        for (ConvertibleMessage msg : this) {
            Map<String, String> map = new HashMap<>();
            for (String key : ((Message) msg).keySet()) {
                map.put(key, msg.getElement(key));
            }
            list.add(new Message(map));
            map.clear();
        }
        return list;
    }
}
