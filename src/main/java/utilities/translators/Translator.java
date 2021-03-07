package utilities.translators;

import org.jetbrains.annotations.NotNull;
import utilities.parsers.ParserNode;

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
    @NotNull List<String> getAvailableLanguages() throws Exception;

    /**
     * This method translates file's parsed values and returns Map with translated nodes.
     * <p>
     * Map data:
     * 1. Keys are language suffixes
     * 2. Values are lists with translator nodes
     * <p>
     * Nodes data:
     * 1. Keys are original values
     * 2. Values are translated values
     *
     * @param parsedValues        list with parsed values
     *                            for getting parsed values by Parser
     * @param languageFrom        language from
     * @param languagesTo         list with languages needed to translate to
     * @param translateAttributes option to translate attributes
     * @return List with translated values
     * @throws Exception if something wrong happened with server
     */
    @NotNull Map<String, List<TranslatorNode>> translate(
            @NotNull List<ParserNode> parsedValues,
            @NotNull String languageFrom,
            @NotNull List<String> languagesTo,
            boolean translateAttributes
    ) throws Exception;
}
