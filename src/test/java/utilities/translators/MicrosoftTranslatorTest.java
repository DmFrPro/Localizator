package utilities.translators;


import constants.Constants;
import models.OriginalMetaFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class MicrosoftTranslatorTest {

    private static final Path XML_EXAMPLE_FILE_PATH = Paths.get(
            Constants.PROJECT_PATH.toString() + "/src/test/resources/xmlExample.xml"
    );


    @Test
    void getAvailableLanguages() throws Exception {
        Translator translator = TranslatorFactory.getDefaultTranslator();
        List<String> languages = translator.getAvailableLanguages();

        Assertions.assertFalse(languages.isEmpty());
    }

    @Test
    void translate() throws Exception {
        OriginalMetaFile originalMetaFile = OriginalMetaFile.newInstance(XML_EXAMPLE_FILE_PATH, "en");
        Translator translator = new MicrosoftTranslator();

        List<String> languagesTo = Arrays.asList("ru", "it");

        Map<String, List<TranslatorNode>> translatedValues = translator.translate(
                originalMetaFile.getParsedValues(),
                "en",
                languagesTo,
                true
        );

        Assertions.assertFalse(translatedValues.isEmpty());
    }
}