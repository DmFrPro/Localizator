package models;

import utilities.parsers.ParserNode;
import utilities.translators.TranslatorNode;

import java.nio.file.Path;
import java.util.List;

/**
 * This is a data-class for keeping necessary information of the translated file.
 *
 * @author dmfrpro
 */
public final class TranslatedMetaFile {

    /**
     * Path for translated file.
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
     * This map will be filled by Translator.
     *
     * @see utilities.translators.Translator
     * <p>
     * Map data:
     * 1. Keys are original values
     * 2. Values are translated values
     */
    private final List<ParserNode> parsedValues;

    /**
     * Original metafile, from which data this translated
     * metafile created.
     *
     * @see OriginalMetaFile
     */
    private final OriginalMetaFile originalMetaFile;

    /**
     * This constructor initializes a new TranslatedMetaFile object.
     *
     * @param path             File's path in a filesystem
     * @param originalMetaFile Original file
     * @see utilities.translators.Translator
     */
    private TranslatedMetaFile(Path path, String fileContent, String language, List<ParserNode> parsedValues, OriginalMetaFile originalMetaFile) {
        this.path = path;
        this.fileContent = fileContent;
        this.language = language;
        this.parsedValues = parsedValues;
        this.originalMetaFile = originalMetaFile;
    }

    public static TranslatedMetaFile newInstance(OriginalMetaFile originalMetaFile, List<TranslatorNode> translatedValues) {
        return null;
    }

    /**
     * Path getter.
     *
     * @return path of the translated file
     */
    public Path getPath() {
        return path;
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
     * Language getter.
     *
     * @return language name
     */
    public String getLanguage() {
        return language;
    }

    /**
     * TranslatedValues getter.
     *
     * @return Map with original and translated values
     */
    public List<ParserNode> getTranslatedValues() {
        return parsedValues;
    }

    /**
     * OriginalMetaFile getter.
     *
     * @return original metafile instance
     * @see OriginalMetaFile
     */
    public OriginalMetaFile getOriginalMetaFile() {
        return originalMetaFile;
    }
}
