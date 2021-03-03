package utilities.parsers;

import constants.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

class ParserFactoryTest {

    private static final Path XML_EXAMPLE_FILE_PATH = Paths.get(
            Constants.PROJECT_PATH.toString() + "/src/test/resources/xmlExample.xml"
    );

    @Test
    void getParserByFileExtension() {
        Parser parser = ParserFactory.getParserByFileExtension(XML_EXAMPLE_FILE_PATH);
        Assertions.assertTrue(parser instanceof XMLParser);
    }
}