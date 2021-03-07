package models;

import constants.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parsers.ParserNode;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class OriginalMetaFileTest {

    private static final Path XML_EXAMPLE_FILE_PATH = Paths.get(
            Constants.PROJECT_PATH.toString() + "/src/test/resources/xmlExample.xml"
    );

    private static final String XML_EXAMPLE_FILE_CONTENT = """
            <xml version="2.0" encoding="UTF-8">
                        
                <!-- tag + twoAttrs + text -->
                <tag0 arg1="hello" arg2="world">hello world</tag0>
                        
                <!-- tag + oneAttr -->
                <tag0 arg1="hello"/>
                        
                <!-- tag + emptyAttrs + text -->
                <tag0>hello world</tag0>
                        
                <!-- tag + emptyAttrs + child tags -->
                <tag1>
                    <!-- tag + twoAttrs + text -->
                    <tag2 arg1="hello" arg2="world">hello world</tag2>
                        
                    <!-- tag + oneAttr + child tags -->
                    <tag3 arg1="hello">
                        
                        <!-- tag + emptyAttrs + text -->
                        <tag4>hello world</tag4>
                    </tag3>
                </tag1>
                        
                <!-- tag + emptyAttrs -->
                <tag5/>
                        
                <!-- tag + twoAttrs + text -->
                <tag6 arg1="hello" arg2="world">hello world</tag6>
            </xml>""";

    private static final String LANGUAGE = "en";

    private static final List<ParserNode> PARSED_VALUES = new LinkedList<>();

    static {
        Map<String, String> twoAttrs = new LinkedHashMap<>();
        twoAttrs.put("arg1", "hello");
        twoAttrs.put("arg2", "world");

        Map<String, String> oneAttr = new LinkedHashMap<>();
        oneAttr.put("arg1", "hello");

        Map<String, String> emptyAttrs = new LinkedHashMap<>();

        PARSED_VALUES.add(new ParserNode("tag0", twoAttrs, "hello world"));
        PARSED_VALUES.add(new ParserNode("tag0", oneAttr, ""));
        PARSED_VALUES.add(new ParserNode("tag0", emptyAttrs, "hello world"));

        PARSED_VALUES.add(new ParserNode("tag1", emptyAttrs, ""));
        PARSED_VALUES.add(new ParserNode("tag2", twoAttrs, "hello world"));

        PARSED_VALUES.add(new ParserNode("tag3", oneAttr, ""));
        PARSED_VALUES.add(new ParserNode("tag4", emptyAttrs, "hello world"));

        PARSED_VALUES.add(new ParserNode("tag5", emptyAttrs, ""));

        PARSED_VALUES.add(new ParserNode("tag6", twoAttrs, "hello world"));
    }

    @Test
    void newInstance() throws Exception {
        MetaFile originalMetaFile = MetaFileFactory.newOriginalMetaFile(XML_EXAMPLE_FILE_PATH, "en");

        Assertions.assertEquals(originalMetaFile.getPath(), XML_EXAMPLE_FILE_PATH);
        Assertions.assertEquals(originalMetaFile.getLanguage(), LANGUAGE);
        Assertions.assertEquals(originalMetaFile.getFileContent(), XML_EXAMPLE_FILE_CONTENT);

        List<ParserNode> parsedValues = originalMetaFile.getParsedValues();

        Assertions.assertEquals(parsedValues.size(), PARSED_VALUES.size());

        for (int i = 0; i < parsedValues.size(); i++) {
            ParserNode currentNode = parsedValues.get(i);
            ParserNode expectedNode = PARSED_VALUES.get(i);

            Assertions.assertEquals(currentNode.getKey(), expectedNode.getKey());
            Assertions.assertEquals(currentNode.getValue(), expectedNode.getValue());

            Assertions.assertEquals(currentNode.getAttributes().size(), expectedNode.getAttributes().size());

            for (Map.Entry<String, String> currentAttr : currentNode.getAttributes().entrySet()) {
                String currentKey = currentAttr.getKey();
                String currentValue = currentAttr.getValue();

                Assertions.assertEquals(currentValue, expectedNode.getAttributes().get(currentKey));
            }
        }
    }
}