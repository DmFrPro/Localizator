package models;

import org.jetbrains.annotations.NotNull;
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
     * This map will be filled by Translator.
     *
     * @see utilities.translators.Translator
     * <p>
     * Map data:
     * 1. Keys are original values
     * 2. Values are translated values
     */
    private final @NotNull List<ParserNode> parsedValues;

    /**
     * Original metafile, from which data this translated
     * metafile created.
     *
     * @see OriginalMetaFile
     */
    private final @NotNull OriginalMetaFile originalMetaFile;

    /**
     * This constructor initializes a new TranslatedMetaFile object.
     *
     * @param path             File's path in a filesystem
     * @param originalMetaFile Original file
     * @see utilities.translators.Translator
     */
    private @NotNull TranslatedMetaFile(@NotNull Path path, @NotNull String fileContent, @NotNull String language, @NotNull List<ParserNode> parsedValues, @NotNull OriginalMetaFile originalMetaFile) {
        this.path = path;
        this.fileContent = fileContent;
        this.language = language;
        this.parsedValues = parsedValues;
        this.originalMetaFile = originalMetaFile;
    }

    public static @NotNull TranslatedMetaFile newInstance(OriginalMetaFile originalMetaFile, List<TranslatorNode> translatedValues) {
        return null;
    }

    /**
     * Path getter.
     *
     * @return path of the translated file
     */
    public @NotNull Path getPath() {
        return path;
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
     * Language getter.
     *
     * @return language name
     */
    public @NotNull String getLanguage() {
        return language;
    }

    /**
     * TranslatedValues getter.
     *
     * @return Map with original and translated values
     */
    public @NotNull List<ParserNode> getTranslatedValues() {
        return parsedValues;
    }

    /**
     * OriginalMetaFile getter.
     *
     * @return original metafile instance
     * @see OriginalMetaFile
     */
    public @NotNull OriginalMetaFile getOriginalMetaFile() {
        return originalMetaFile;
    }
}
