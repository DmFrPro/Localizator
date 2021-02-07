package utilities.translators;

import models.OriginalMetaFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Translators in this application translates files to Map with String keys and values.
 * <p>
 * Translator's requirements are:
 * 1. Get file's parsed values
 * 2. Translate it and return new Map
 *
 * @author dmfrpro
 */
public interface Translator {

    /**
     * This method returns available languages that can be used.
     *
     * @return List with languages as String
     */
    @Nullable List<String> getAvailableLanguages();

    /**
     * This method translates file's parsed values and returns Map with translated data.
     * Map data:
     * 1. Keys are original values
     * 2. Values are translated values
     *
     * @param originalMetaFile instance of original file
     *                         for getting parsed values by Parser
     * @param languageFrom     language from
     * @param languageTo       language to
     * @return Map with original and translated values
     * @see OriginalMetaFile
     * @see utilities.translators.TranslatorNode
     */
    @NotNull List<TranslatorNode> translate(@NotNull OriginalMetaFile originalMetaFile, @NotNull String languageFrom, @NotNull String languageTo);
}
