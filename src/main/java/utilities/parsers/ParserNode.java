package utilities.parsers;

/**
 * This data-class is used by parsers.
 * 1. ParserNode's key ia a tag with arguments.
 * 2. ParserNode's value is a value of the tag.
 *
 * @author dmfrpro
 */
public class ParserNode {
    /**
     * ParserNode's key ia a tag with arguments.
     *
     * For example, <p style="text-align: justify">Hello world!</p>
     *  * Key is <p style="text-align: justify">
     */
    private final String key;

    /**
     * ParserNode's value is a value of the tag.
     *
     * For example, <p style="text-align: justify">Hello world!</p>
     *  * Key is <p style="text-align: justify">
     *  * Value is Hello world!
     */
    private final String value;

    /**
     * This constructor creates default instance with filled fields.
     *
     * @param key tag with arguments
     * @param value value of the tag
     */
    public ParserNode(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Key getter.
     *
     * @return tag with arguments
     */
    public String getKey() {
        return key;
    }

    /**
     * Value getter.
     *
     * @return value of the tag
     */
    public String getValue() {
        return value;
    }
}
