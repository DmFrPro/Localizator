package utilities;

import org.jetbrains.annotations.NotNull;
import utilities.parsers.Parser;

import java.nio.file.Path;

/**
 * This utilitarian class contains useful methods
 * for working with parsers.
 *
 * @author dmfrpro
 */
public final class ParserHelper {

    /**
     * Utilitarian classes must have private constructors
     * to prevent creation instances.
     */
    private ParserHelper() {
    }

    /**
     * This method returns Parser instance by file's extension.
     *
     * @param path file's path
     * @return Parser instance
     * @see Parser
     */
    public static @NotNull Parser getParserByFileExtension(@NotNull Path path) {
        return null;
    }
}
