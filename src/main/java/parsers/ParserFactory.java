package parsers;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * This utilitarian class contains useful methods
 * for working with parsers.
 *
 * @author dmfrpro
 */
public final class ParserFactory {

    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(ParserFactory.class);

    /**
     * This map contains a parsers.
     *
     * Map data:
     * 1. Keys are file extensions
     */
    private static final Map<String, Parser> parsers = new HashMap<>();

    static {
        parsers.put(".xml", new XMLParser());
    }

    /**
     * Utilitarian classes must have private constructors
     * to prevent creation instances.
     */
    private ParserFactory() {
    }

    /**
     * This method returns Parser instance by file extension.
     *
     * @param path file's path
     * @return Parser instance
     * @see Parser
     */
    public static @NotNull Parser getParserByFileExtension(@NotNull Path path) {
        if (path.toFile().isDirectory()) {
            logger.error("Given path for method getParserByFileExtension() is not the file");
            throw new IllegalArgumentException("Given path is not the file");
        }

        String pathAsString = path.toString();

        return parsers.get(pathAsString.substring(pathAsString.lastIndexOf('.')));
    }
}
