package utilities.parsers;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

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
     * <p>
     * For example, <p style="text-align: justify">Hello world!</p>
     * Key is p
     */
    private final @NotNull String key;

    /**
     * ParserNode's attributes ia a map with args and values.
     * <p>
     * 1. Map key is an argument name
     * 2. Map value is an argument value
     *
     * For example, <p style="text-align: justify">Hello world!</p>
     * Key is style
     * Value is text-align: justify
     */
    private final @NotNull Map<String, String> attributes;

    /**
     * ParserNode's value is a value of the tag.
     * <p>
     * For example, <p style="text-align: justify">Hello world!</p>
     * * Value is Hello world!
     */
    private final @NotNull String value;

    /**
     * This constructor creates default instance with filled fields.
     *
     * @param key        tag
     * @param value      value of the tag
     */
    public @NotNull ParserNode(@NotNull String key, @NotNull Map<String, String> attributes, @NotNull String value) {
        this.key = key;
        this.attributes = attributes;
        this.value = value;
    }

    /**
     * Key getter.
     *
     * @return tag with arguments
     */
    public @NotNull String getKey() {
        return key;
    }

    /**
     * Attributes getter.
     *
     * @return Map with attributes
     */
    public @NotNull Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * Value getter.
     *
     * @return value of the tag
     */
    public @NotNull String getValue() {
        return value;
    }
}
