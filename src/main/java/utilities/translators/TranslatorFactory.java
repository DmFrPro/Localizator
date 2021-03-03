package utilities.translators;

/**
 * This utilitarian class contains useful methods for
 * working with translators.
 *
 * @author dmfrpro
 */
public final class TranslatorFactory {

    /**
     * Default translator.
     */
    private static final Translator defaultTranslator = new MicrosoftTranslator();

    /**
     * This method returns new translator instance.
     *
     * @return default translator instance
     */
    public static Translator getDefaultTranslator() {
        return defaultTranslator;
    }
}
