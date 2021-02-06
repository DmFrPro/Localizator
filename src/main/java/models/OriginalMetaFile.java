package models;

import utilities.parsers.ParserNode;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a data-class for keeping necessary information of the original file.
 *
 * @author dmfrpro
 */
public class OriginalMetaFile {

    /**
     * Path for original file.
     */
    private final Path path;

    /**
     * File's content in a String object.
     */
    private final String fileContent;

    /**
     * Language name.
     */
    private final String language;

    /**
     * This list will be filled by Parser.
     *
     * @see utilities.parsers.Parser
     * @see ParserNode
     */
    private final List<ParserNode> parsedValues;

    /**
     * This map contains translated metafiles.
     * <p>
     * Map data:
     * 1. Keys are a paths of translated files
     * 2. Values are a TranslatedMetaFile objects
     *
     * @see TranslatedMetaFile
     */
    private final Map<Path, TranslatedMetaFile> translatedMetaFiles;

    /**
     * This private constructor initializes a new instance of original metafile.
     * You can init a new instance of original metafile by using getInstance() method.
     *
     * @param path         file's path in filesystem
     * @param fileContent  file's content as String
     * @param language     file's language
     * @param parsedValues parsed list by Parser
     * @see utilities.parsers.Parser
     * @see ParserNode
     */
    private OriginalMetaFile(Path path, String fileContent, String language, List<ParserNode> parsedValues) {
        this.path = path;
        this.fileContent = fileContent;
        this.language = language;
        this.parsedValues = parsedValues;
        this.translatedMetaFiles = new HashMap<>();
    }

    /**
     * This method automatically initializes OriginalMetaFile instance.
     *
     * @param path file's path in filesystem
     * @return OriginalMetaFile instance
     */
    public static OriginalMetaFile newInstance(Path path) {
        return null;
    }

    /**
     * Path getter.
     *
     * @return path of the original file
     */
    public Path getPath() {
        return path;
    }

    /**
     * Language getter.
     *
     * @return language name
     */
    public String getLanguage() {
        return language;
    }

    /**
     * FileContent getter.
     *
     * @return original file's content
     */
    public String getFileContent() {
        return fileContent;
    }

    /**
     * OriginalValues getter.
     *
     * @return List with ParserNode and values of these tags
     * @see ParserNode
     */
    public List<ParserNode> getParsedValues() {
        return parsedValues;
    }

    /**
     * OriginalValues getter.
     *
     * @return Map with paths and TranslatedMetaFile objects
     * @see TranslatedMetaFile
     */
    public Map<Path, TranslatedMetaFile> getTranslatedMetaFiles() {
        return translatedMetaFiles;
    }
}
