package parsers;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;

/**
 * Parsers in this application parses files to Map with String keys and values.
 * <p>
 * Parser's requirements are:
 * 1. Load file's content
 * 2. Parse it to List with parser nodes and return
 *
 * @author dmfrpro
 */
public interface Parser {
    /**
     * This method parses file and returns Map with parse data.
     * List data is a ParserNode instances:
     * 1. ParserNode's key ia a tag with arguments.
     * 2. ParserNode's value is a value of the tag.
     *
     * For example, <p style="text-align: justify">Hello world!</p>
     * Key is <p style="text-align: justify">
     * Value is Hello world!
     *
     * @param path file's path in filesystem
     * @return List with tags and values
     * @see ParserNode
     */
    @NotNull List<ParserNode> parse(@NotNull Path path) throws Exception;
}
