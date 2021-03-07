package models;

import constants.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parsers.ParserNode;
import translators.Translator;
import translators.TranslatorFactory;
import translators.TranslatorNode;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class MetaFileFactoryTest {

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
    

    @Test
    void newOriginalMetaFile() throws Exception {
        MetaFile expectedOriginalMetaFile = OriginalMetaFile.newInstance(XML_EXAMPLE_FILE_PATH, "en");
        MetaFile originalMetaFile = MetaFileFactory.newOriginalMetaFile(XML_EXAMPLE_FILE_PATH, "en");
        
        Assertions.assertEquals(originalMetaFile.getPath(), expectedOriginalMetaFile.getPath());
        Assertions.assertEquals(originalMetaFile.getFileContent(), expectedOriginalMetaFile.getFileContent());
        Assertions.assertEquals(originalMetaFile.getLanguage(), expectedOriginalMetaFile.getLanguage());
        
        int size = originalMetaFile.getParsedValues().size();
        int expectedSize = expectedOriginalMetaFile.getParsedValues().size();
        
        Assertions.assertEquals(size, expectedSize);

        for (int i = 0; i < size; i++) {
            ParserNode node = originalMetaFile.getParsedValues().get(i);
            ParserNode expectedNode = expectedOriginalMetaFile.getParsedValues().get(i);
            
            Assertions.assertEquals(node.getKey(), expectedNode.getKey());
            Assertions.assertEquals(node.getValue(), expectedNode.getValue());
            
            Assertions.assertEquals(node.getAttributes().size(), expectedNode.getAttributes().size());
            
            for (Map.Entry<String, String> attr : node.getAttributes().entrySet()) {
                String key = attr.getKey();
                String value = attr.getValue();
                
                Assertions.assertEquals(value, expectedNode.getAttributes().get(key));
            }
        }
    }

    @Test
    void newTranslatedMetaFile() throws Exception {
        MetaFile originalMetaFile = OriginalMetaFile.newInstance(XML_EXAMPLE_FILE_PATH, "en");

        Translator translator = TranslatorFactory.getDefaultTranslator();

        Map<String, List<TranslatorNode>> translatedValues = translator.translate(
                originalMetaFile.getParsedValues(),
                originalMetaFile.getLanguage(),
                Collections.singletonList("ru"),
                true
        );

        MetaFile expectedTranslatedMetaFile = TranslatedMetaFile.newInstance(
                originalMetaFile,
                "ru",
                translatedValues.get("ru")
        );

        MetaFile translatedMetaFile = MetaFileFactory.newTranslatedMetaFile(
                originalMetaFile,
                "ru",
                translatedValues.get("ru")
        );

        Assertions.assertEquals(translatedMetaFile.getPath(), expectedTranslatedMetaFile.getPath());
        Assertions.assertEquals(translatedMetaFile.getFileContent(), expectedTranslatedMetaFile.getFileContent());
        Assertions.assertEquals(translatedMetaFile.getLanguage(), expectedTranslatedMetaFile.getLanguage());

        int size = translatedMetaFile.getParsedValues().size();
        int expectedSize = expectedTranslatedMetaFile.getParsedValues().size();

        Assertions.assertEquals(size, expectedSize);

        for (int i = 0; i < size; i++) {
            ParserNode node = translatedMetaFile.getParsedValues().get(i);
            ParserNode expectedNode = expectedTranslatedMetaFile.getParsedValues().get(i);

            Assertions.assertEquals(node.getKey(), expectedNode.getKey());
            Assertions.assertEquals(node.getValue(), expectedNode.getValue());

            Assertions.assertEquals(node.getAttributes().size(), expectedNode.getAttributes().size());

            for (Map.Entry<String, String> attr : node.getAttributes().entrySet()) {
                String key = attr.getKey();
                String value = attr.getValue();

                Assertions.assertEquals(value, expectedNode.getAttributes().get(key));
            }
        }
    }

    @Test
    void readFileContent() throws IOException {
        String fileContent = MetaFileFactory.readFileContent(XML_EXAMPLE_FILE_PATH);
        Assertions.assertEquals(fileContent, XML_EXAMPLE_FILE_CONTENT);
    }

    @Test
    void writeTranslatedMetaFile() throws Exception {
        MetaFile originalMetaFile = OriginalMetaFile.newInstance(XML_EXAMPLE_FILE_PATH, "en");

        Translator translator = TranslatorFactory.getDefaultTranslator();

        Map<String, List<TranslatorNode>> translatedValues = translator.translate(
                originalMetaFile.getParsedValues(),
                originalMetaFile.getLanguage(),
                Collections.singletonList("ru"),
                true
        );

        MetaFile translatedMetaFile = TranslatedMetaFile.newInstance(
                originalMetaFile,
                "ru",
                translatedValues.get("ru")
        );

        Assertions.assertEquals(translatedMetaFile.getFileContent(), XML_EXAMPLE_TRANSLATED_FILE_CONTENT);
    }

    @Test
    void getPathWithLanguageSuffix() {
        Path newPath = MetaFileFactory.getPathWithLanguageSuffix(XML_EXAMPLE_FILE_PATH, "ru");

        Path expectedPath = Paths.get(
                Constants.PROJECT_PATH.toString() + "/src/test/resources-ru/xmlExample.xml"
        );

        Assertions.assertEquals(newPath, expectedPath);
    }
}