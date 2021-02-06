package utilities.translators;

/**
 * This data-class is used by parsers.
 * 1. TranslatorNode's key ia a tag with arguments.
 * 2. TranslatorNode's value is a value of the tag.
 *
 * @author dmfrpro
 */
public class TranslatorNode {
    /**
     * A value on original language.
     */
    private final String languageFromValue;

    /**
     * A value on translated language.
     */
    private final String languageToValue;

    /**
     * This constructor creates default instance with filled fields.
     *
     * @param languageFromValue value on original language
     * @param languageToValue   value on translated language
     */
    public TranslatorNode(String languageFromValue, String languageToValue) {
        this.languageFromValue = languageFromValue;
        this.languageToValue = languageToValue;
    }

    /**
     * Key getter.
     *
     * @return value on original language
     */
    public String getKey() {
        return languageFromValue;
    }

    /**
     * Value getter.
     *
     * @return value on translated language
     */
    public String getLanguageToValue() {
        return languageToValue;
    }
}
