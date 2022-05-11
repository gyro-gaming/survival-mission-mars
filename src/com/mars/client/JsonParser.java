package com.mars.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonParser {

    /**
     * method parses .json into a Java usable map created by
     * Jackson library
     * @param file
     * @return Map<String, Object>
     */
    public static Map<String, Object> parseJson(String file) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        try {
            map = mapper.readValue(new FileInputStream (file), new TypeReference<Map<String, Object>>(){});
        } catch (IOException e) {
            System.out.println("Sorry there are no saved games.");
            Game.quit();
        }
        return map;
    }


}
