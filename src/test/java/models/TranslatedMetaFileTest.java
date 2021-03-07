package models;

import constants.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utilities.parsers.ParserNode;
import utilities.translators.Translator;
import utilities.translators.TranslatorFactory;
import utilities.translators.TranslatorNode;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class TranslatedMetaFileTest {

    private static final Path XML_EXAMPLE_FILE_PATH = Paths.get(
            Constants.PROJECT_PATH.toString() + "/src/test/resources/xmlExample.xml"
    );

    private static final Path XML_TRANSLATED_FILE_PATH = Paths.get(
            Constants.PROJECT_PATH.toString() + "/src/test/resources-ru/xmlExample.xml"
    );

    private static final String XML_EXAMPLE_TRANSLATED_FILE_CONTENT = """
            <xml version="2.0" encoding="UTF-8">
                        
                <!-- tag + twoAttrs + text -->
                <tag0 arg1="Привет" arg2="Мире">Всем привет</tag0>
                        
                <!-- tag + oneAttr -->
                <tag0 arg1="Привет"/>
                        
                <!-- tag + emptyAttrs + text -->
                <tag0>Всем привет</tag0>
                        
                <!-- tag + emptyAttrs + child tags -->
                <tag1>
                    <!-- tag + twoAttrs + text -->
                    <tag2 arg1="Привет" arg2="Мире">Всем привет</tag2>
                        
                    <!-- tag + oneAttr + child tags -->
                    <tag3 arg1="Привет">
                        
                        <!-- tag + emptyAttrs + text -->
                        <tag4>Всем привет</tag4>
                    </tag3>
                </tag1>
                        
                <!-- tag + emptyAttrs -->
                <tag5/>
                        
                <!-- tag + twoAttrs + text -->
                <tag6 arg1="Привет" arg2="Мире">Всем привет</tag6>
            </xml>""";

    private static final String LANGUAGE = "ru";

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

        Translator translator = TranslatorFactory.getDefaultTranslator();

        Map<String, List<TranslatorNode>> translatedValues = translator.translate(
                originalMetaFile.getParsedValues(),
                originalMetaFile.getLanguage(),
                Collections.singletonList("ru"),
                true
        );

        MetaFile translatedMetaFile = MetaFileFactory.newTranslatedMetaFile(
                originalMetaFile,
                "ru",
                translatedValues.get("ru")
        );

        Assertions.assertEquals(translatedMetaFile.getPath(), XML_TRANSLATED_FILE_PATH);
        Assertions.assertEquals(translatedMetaFile.getLanguage(), LANGUAGE);
        Assertions.assertEquals(translatedMetaFile.getFileContent(), XML_EXAMPLE_TRANSLATED_FILE_CONTENT);

        List<ParserNode> parsedValues = translatedMetaFile.getParsedValues();

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