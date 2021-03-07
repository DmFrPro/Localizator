package translators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TranslatorFactoryTest {

    @Test
    void getDefaultTranslator() {
        Translator defaultTranslator = TranslatorFactory.getDefaultTranslator();
        Assertions.assertTrue(defaultTranslator instanceof MicrosoftTranslator);
    }
}