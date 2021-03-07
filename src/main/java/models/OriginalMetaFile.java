package models;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parsers.ParserFactory;
import parsers.ParserNode;

import java.nio.file.Path;
import java.util.List;

/**
 * This is a data-class for keeping necessary information of the original file.
 *
 * @author dmfrpro
 */
class OriginalMetaFile implements MetaFile {

    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(OriginalMetaFile.class);

    /**
     * Path for original file.
     */
    private final @NotNull Path path;

    /**
     * File's content in a String object.
     */
    private final @NotNull String fileContent;

    /**
     * Language name.
     */
    private final @NotNull String language;

    /**
     * This list will be filled by Parser.
     *
     * @see parsers.Parser
     * @see ParserNode
     */
    private final @NotNull List<ParserNode> parsedValues;

    /**
     * This private constructor initializes a new instance of original metafile.
     * You can init a new instance of original metafile by using getInstance() method.
     *
     * @param path         file's path in filesystem
     * @param fileContent  file's content as String
     * @param language     file's language
     * @param parsedValues parsed list by Parser
     * @see parsers.Parser
     * @see ParserNode
     */
    private @NotNull OriginalMetaFile(
            @NotNull Path path,
            @NotNull String fileContent,
            @NotNull String language,
            @NotNull List<ParserNode> parsedValues
    ) {
        this.path = path;
        this.fileContent = fileContent;
        this.language = language;
        this.parsedValues = parsedValues;
    }

    /**
     * This method automatically initializes OriginalMetaFile instance.
     *
     * @param path file's path in filesystem
     * @return OriginalMetaFile instance
     */
    public static @NotNull OriginalMetaFile newInstance(
            @NotNull Path path,
            @NotNull String language
    ) throws Exception {

        logger.info("Creating OriginalMetaFile from file " + path.toString());

        String fileContent = MetaFileFactory.readFileContent(path);

        // Parse the file
        List<ParserNode> parsedValues = ParserFactory
                .getParserByFileExtension(path)
                .parse(path);

        logger.info("Successfully created OriginalMetaFile from file " + path.toString());

        return new OriginalMetaFile(path, fileContent, language, parsedValues);
    }

    /**
     * Path getter.
     *
     * @return path of the original file
     */
    public @NotNull Path getPath() {
        return path;
    }

    /**
     * File's language.
     *
     * @return language suffix as String
     */
    public @NotNull String getLanguage() {
        return language;
    }

    /**
     * FileContent getter.
     *
     * @return original file's content
     */
    public @NotNull String getFileContent() {
        return fileContent;
    }

    /**
     * OriginalValues getter.
     *
     * @return List with ParserNode and values of these tags
     * @see ParserNode
     */
    public @NotNull List<ParserNode> getParsedValues() {
        return parsedValues;
    }
}
