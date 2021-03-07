package models;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.parsers.ParserNode;
import utilities.translators.TranslatorNode;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

/**
 * This is a data-class for keeping necessary information of the translated file.
 *
 * @author dmfrpro
 */
class TranslatedMetaFile implements MetaFile {

    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(OriginalMetaFile.class);

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
     * This list will be filled by Parser.
     *
     * @see utilities.parsers.Parser
     * @see ParserNode
     */
    private final @NotNull List<ParserNode> parsedValues;

    /**
     * This map will be filled by Translator.
     *
     * @see utilities.translators.Translator
     * <p>
     * Map data:
     * 1. Keys are original values
     * 2. Values are translated values
     */
    private final @NotNull List<TranslatorNode> translatedValues;

    /**
     * Original metafile, from which data this translated
     * metafile created.
     *
     * @see OriginalMetaFile
     */
    private final @NotNull MetaFile originalMetaFile;

    /**
     * This constructor initializes a new TranslatedMetaFile object.
     *
     * @param path             File's path in a filesystem
     * @param originalMetaFile Original file
     * @see utilities.translators.Translator
     */
    private @NotNull TranslatedMetaFile(
            @NotNull Path path,
            @NotNull String fileContent,
            @NotNull String language,
            @NotNull List<ParserNode> parsedValues,
            @NotNull List<TranslatorNode> translatedValues,
            @NotNull MetaFile originalMetaFile) {
        this.path = path;
        this.fileContent = fileContent;
        this.language = language;
        this.parsedValues = parsedValues;
        this.translatedValues = translatedValues;
        this.originalMetaFile = originalMetaFile;
    }

    /**
     * This method automatically initializes TranslatedMetaFile instance.
     *
     * @param originalMetaFile original metafile
     * @param language         file's language suffix
     * @param translatedValues list with translated values
     * @return translated metafile
     * @see TranslatorNode
     */
    public static @NotNull TranslatedMetaFile newInstance(
            @NotNull MetaFile originalMetaFile,
            @NotNull String language,
            @NotNull List<TranslatorNode> translatedValues
    ) {

        logger.info("Creating TranslatedMetaFile from file " + originalMetaFile.getPath().toString());

        // Add suffix to the parent folder
        Path newPath = MetaFileFactory.getPathWithLanguageSuffix(originalMetaFile.getPath(), language);

        // Generate the new file content with translated values
        String newFileContent = originalMetaFile.getFileContent();

        /*
        We must sort translated values to prevent replace errors
        For example:
        value1 = "hello world"
        value2 = "he said "hello world" to us"

        Translated value1 = "привет мир"
        Translated value2 = "он сказал нам "привет мир""

        If we replace a value1 first, we will get a "he said "привет мир" to us"
        Then, we won't be able to replace a value2
         */
        translatedValues.sort(
                Comparator.comparingInt(x -> {
                            TranslatorNode y = (TranslatorNode) x;
                            return y.getLanguageToValue().length();
                        }
                ).reversed()
        );

        // Replace to the translated values
        for (TranslatorNode node : translatedValues) {
            newFileContent = newFileContent.replace(
                    ">" + node.getLanguageFromValue() + "<", ">" + node.getLanguageToValue() + "<"
            );

            newFileContent = newFileContent.replace(
                    "\"" + node.getLanguageFromValue() + "\"", "\"" + node.getLanguageToValue() + "\""
            );
        }

        List<ParserNode> parsedValues = originalMetaFile.getParsedValues();

        TranslatedMetaFile translatedMetaFile = new TranslatedMetaFile(
                newPath,
                newFileContent,
                language,
                parsedValues,
                translatedValues,
                originalMetaFile
        );

        logger.info("Successfully created TranslatedMetaFile with path " + newPath.toString());

        return translatedMetaFile;
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
     * File's language.
     *
     * @return language suffix as String
     */
    public @NotNull String getLanguage() {
        return language;
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

    /**
     * TranslatedValues getter.
     *
     * @return Map with original and translated values
     */
    public @NotNull List<TranslatorNode> getTranslatedValues() {
        return translatedValues;
    }

    /**
     * OriginalMetaFile getter.
     *
     * @return original metafile instance
     * @see OriginalMetaFile
     */
    public @NotNull MetaFile getOriginalMetaFile() {
        return originalMetaFile;
    }
}
