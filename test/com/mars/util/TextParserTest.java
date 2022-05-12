package com.mars.util;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TextParserTest {
    TextParser textParser;

    @Before
    public void init() {
        textParser = new TextParser();
    }

    @Test
    public void cleanUserInput() {

    }

    @Test
    public void getCommand() {
        List<String> result = new ArrayList<>();
        result.add("look");
        result.add("north");
        assertEquals(textParser.getCommand("look north"), result);
    }
}