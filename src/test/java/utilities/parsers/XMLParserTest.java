package utilities.parsers;

import constants.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Test class for XML parser.
 *
 * @author dmfrpro
 * @see XMLParser
 */
class XMLParserTest {

    /**
     * Test XML file path.
     */
    private static final Path XML_EXAMPLE_FILE_PATH = Paths.get(
            Constants.PROJECT_PATH.toString() + "/src/test/resources/xmlExample.xml"
    );

    /**
     * List of parsed values.
     */
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

    /**
     * Expected parsed values size.
     */
    private static final int PARSED_VALUES_SIZE = PARSED_VALUES.size();

    /**
     * This method is a test method for XML parser.
     *
     * @throws ParserConfigurationException if SAX parser can't be configured
     * @throws SAXException                 if SAX parser can't be created
     * @throws IOException                  if SAX parser got an incorrect file in parsing process
     * @see XMLParser
     */
    @Test
    void parse() throws ParserConfigurationException, SAXException, IOException {
        XMLParser parser = new XMLParser();
        List<ParserNode> parsedValues = parser.parse(XML_EXAMPLE_FILE_PATH);

        // Check the size of parsed values
        Assertions.assertEquals(parsedValues.size(), PARSED_VALUES_SIZE);

        for (int i = 0; i < parsedValues.size(); i++) {
            ParserNode currentNode = parsedValues.get(i);
            ParserNode expectedNode = PARSED_VALUES.get(i);

            // Check the name and value of every parsed node
            Assertions.assertEquals(currentNode.getKey(), expectedNode.getKey());
            Assertions.assertEquals(currentNode.getValue(), expectedNode.getValue());

            // Check the attrs amount of every parsed node
            Assertions.assertEquals(currentNode.getAttributes().size(), expectedNode.getAttributes().size());

            // Check the attrs completely
            for (Map.Entry<String, String> currentAttr : currentNode.getAttributes().entrySet()) {
                String currentKey = currentAttr.getKey();
                String currentValue = currentAttr.getValue();

                Assertions.assertEquals(currentValue, expectedNode.getAttributes().get(currentKey));
            }
        }
    }
}