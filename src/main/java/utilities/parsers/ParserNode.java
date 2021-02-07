package utilities.parsers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
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
    public @NotNull ParserNode(@NotNull String key, @NotNull String value) {
        this.key = key;
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
     * Value getter.
     *
     * @return value of the tag
     */
    public @NotNull String getValue() {
        return value;
    }
}
