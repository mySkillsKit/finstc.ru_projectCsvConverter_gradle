package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.ConvertibleMessage;

import java.util.HashMap;
import java.util.Map;

public class Message extends HashMap<String, String> implements ConvertibleMessage {


    public Message(Map<String, String> map) {
        this.putAll(map);
    }

    @Override
    public String getElement(String elementId) {
        return this.get(elementId);
    }
}

