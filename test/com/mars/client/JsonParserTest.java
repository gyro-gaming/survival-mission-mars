package com.mars.client;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonParserTest {

    @Before
    public void init() {
    }

    @Test
    public void parseJson_shouldBeNotNull_whenFilePassedIn() {
        String file = "data/json/rooms.json";
        JsonParser.parseJson(file);
        assertNotNull(JsonParser.parseJson(file));
    }
}