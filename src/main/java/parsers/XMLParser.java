package parsers;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * This is a Parser implementation for XML files.
 * Brought up with SAX parser.
 *
 * @author dmfrpro
 * @see parsers.Parser
 */
class XMLParser implements Parser {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(XMLParser.class);

    /**
     * This method parses an XML file and returns Map with parse data.
     * List data is a ParserNode instances:
     * 1. ParserNode's key ia a tag with arguments.
     * 2. ParserNode's value is a value of the tag.
     * <p>
     * For example, <p style="text-align: justify">Hello world!</p>
     * Key is <p style="text-align: justify">
     * Value is Hello world!
     *
     * @param path file's path in filesystem
     * @return List with tags and values
     * @throws ParserConfigurationException if SAX parser can't be configured
     * @throws SAXException                 if SAX parser can't be created
     * @throws IOException                  if SAX parser got an incorrect file in parsing process
     * @see Parser
     */
    @Override
    public @NotNull List<ParserNode> parse(@NotNull Path path) throws ParserConfigurationException, SAXException, IOException {

        // Get SAX parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        // This list will be returned
        List<ParserNode> parsedValues = new LinkedList<>();

        // SAX handler implementation
        DefaultHandler handler = new DefaultHandler() {


            String name;                            // Tag's name
            Map<String, String> parsedAttributes;   // Tag's attrs
            String value;                           // Tag's text value

            // This stack keeps tags' names last used
            final Stack<String> tags = new Stack<>();

            @Override
            public void startDocument() {
                logger.info(String.format("Started parsing file %s", path.toString()));
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {

                if (qName.equals("xml"))
                    return;

                name = qName;
                parsedAttributes = new LinkedHashMap<>();

                // Parse the attrs
                for (int i = 0; i < attributes.getLength(); i++) {
                    parsedAttributes.put(attributes.getQName(i), attributes.getValue(i));
                }

                // We need to save tag's name
                tags.push(qName);
            }

            @Override
            public void characters(char[] ch, int start, int length) {
                value = new String(ch, start, length);

                // In some situations there are a tags with a strange text values
                // These values are just a sequence of spaces, so let's cut them to empty value
                if (value.matches("\\s+")) {
                    value = "";
                }

                // With some tag we get null parameter name
                // This stack checks if we can add a parsed node
                // Also, don't forget to pop last tag name
                if (tags.size() > 0 && tags.pop().equals(name)) {
                    parsedValues.add(new ParserNode(name, parsedAttributes, value));
                }
            }

            @Override
            public void endDocument() {
                logger.info(String.format("File %s was parsed successfully", path.toString()));
            }
        };

        saxParser.parse(path.toFile(), handler);
        return parsedValues;
    }
}