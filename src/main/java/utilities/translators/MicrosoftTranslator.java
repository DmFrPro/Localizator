package utilities.translators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.parsers.ParserNode;

import java.io.IOException;
import java.util.*;

class MicrosoftTranslator implements Translator {

    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(Translator.class);

    /**
     * OkHttpClient instance.
     */
    private static final OkHttpClient client = new OkHttpClient();

    /**
     * Json mapper instance.
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * API-specific variables.
     */
    private static final String API_PROTOCOL = "https";
    private static final String API_ENDPOINT = "api.cognitive.microsofttranslator.com";
    private static final String API_SOAP_TYPE = "application/json";
    private static final String API_KEY = "1b103f78822349e4a6590091aa6603a2";

    /**
     * Json-nodes' names.
     */
    private static final String JSON_TEXT_NODE = "text";
    private static final String JSON_ROOT_NODE = "translation";
    private static final String JSON_TRANSLATIONS_NODE = "translations";
    private static final String JSON_TO_NODE = "to";


    /**
     * This method returns all available languages translator can observe.
     * <p>
     * List data:
     * 1. Language suffix as string
     * <p>
     * For example: [.... en, es, et, ... ru, ... ]
     *
     * @return List with languages' suffixes
     * @throws Exception if something wrong happened with server
     * @see Translator
     */
    @Override
    public @NotNull List<String> getAvailableLanguages() throws Exception {

        logger.info("Requesting for available languages");

        HttpUrl url = new HttpUrl.Builder()
                .scheme(API_PROTOCOL)
                .host(API_ENDPOINT)
                .addPathSegment("/languages")
                .addQueryParameter("api-version", "3.0")
                .addQueryParameter("scope", "translation")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Content-type", API_SOAP_TYPE)
                .build();

        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();

        Objects.requireNonNull(responseBody);

        JsonNode parsedJson = parseJson(responseBody.string());

        List<String> languages = new LinkedList<>();

        Iterator<Map.Entry<String, JsonNode>> nodes = parsedJson.findValue(JSON_ROOT_NODE).fields();

        while (nodes.hasNext()) {
            Map.Entry<String, JsonNode> entry = nodes.next();
            languages.add(entry.getKey());
        }

        logger.info("Successfully got available languages list");

        return languages;
    }

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
     * @see Translator
     */
    @Override
    public @NotNull Map<String, List<TranslatorNode>> translate(
            @NotNull List<ParserNode> parsedValues,
            @NotNull String languageFrom,
            @NotNull List<String> languagesTo,
            boolean translateAttributes
    ) throws Exception {

        logger.info("Starting translating values");

        // This list will be used to get Json with translated values
        List<String> values = new LinkedList<>();

        // Add text values
        for (ParserNode parserNode : parsedValues) {

            // Add only not empty values
            if (!parserNode.getValue().isEmpty()) {
                values.add(parserNode.getValue());
            }
        }

        // Add attributes if need
        if (translateAttributes) {
            for (ParserNode parserNode : parsedValues) {

                // Add only not empty attributes
                if (parserNode.getAttributes().size() != 0) {
                    for (Map.Entry<String, String> attrs : parserNode.getAttributes().entrySet()) {
                        values.add(attrs.getValue());
                    }
                }
            }
        }

        Map<String, List<TranslatorNode>> translatedValues = getEmptyTranslatedValues(languagesTo);
        List<JsonNode> jsonNodes = getTranslatedValuesAsJsonsList(values, languageFrom, languagesTo);

        for (int i = 0; i < values.size(); i++) {
            String languageFromValue = values.get(i);

            jsonNodes.get(i).findValue(JSON_TRANSLATIONS_NODE).forEach(x -> {
                String languageToValue = x.get(JSON_TEXT_NODE).textValue();
                String languageTo = x.get(JSON_TO_NODE).textValue();

                translatedValues.get(languageTo).add(new TranslatorNode(languageFromValue, languageToValue));
            });
        }

        logger.info("Successfully got translated values");

        return translatedValues;
    }

    /**
     * This method returns configured empty linked hashmap.
     * <p>
     * Map data:
     * 1. Keys are languages as String
     * 2. Values are empty linked lists
     *
     * @param languagesTo list with languages needed to translate to
     * @return empty configured linked hashmap
     */
    private Map<String, List<TranslatorNode>> getEmptyTranslatedValues(List<String> languagesTo) {
        Map<String, List<TranslatorNode>> translatedValues = new LinkedHashMap<>();
        languagesTo.forEach(x -> translatedValues.put(x, new LinkedList<>()));
        return translatedValues;
    }

    /**
     * This method performs post request and returns Json-object
     * with translated values as String.
     *
     * @param values       values as String to be translated
     * @param languageFrom translation from this language
     * @param languagesTo  translation to this language
     * @return Json-object with translated values as String
     * @throws IOException if something wrong happened with server
     * @see ParserNode
     * @see models.MetaFile
     */
    private @NotNull List<JsonNode> getTranslatedValuesAsJsonsList(
            @NotNull List<String> values,
            @NotNull String languageFrom,
            @NotNull List<String> languagesTo
    ) throws IOException {

        logger.info("Requesting json with translated values");

        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme(API_PROTOCOL)
                .host(API_ENDPOINT)
                .addPathSegment("/translate")
                .addQueryParameter("api-version", "3.0")
                .addQueryParameter("from", languageFrom);

        for (String language : languagesTo) {
            builder.addQueryParameter("to", language);
        }

        HttpUrl url = builder.build();

        List<JsonNode> jsonNodes = new ArrayList<>();

        for (String value : values) {
            String json = convertToJsonAsString(value);

            RequestBody body = RequestBody.create(json, MediaType.parse(API_SOAP_TYPE));

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Ocp-Apim-Subscription-Key", API_KEY)
                    .addHeader("Content-type", API_SOAP_TYPE)
                    .build();

            Response response = client.newCall(request).execute();

            ResponseBody responseBody = response.body();
            Objects.requireNonNull(responseBody);

            jsonNodes.add(parseJson(responseBody.string()));
        }

        logger.info("Successfully got json with translated values");

        return jsonNodes;
    }

    /**
     * This method parses Json to JsonNode instance.
     *
     * @param json Json-object as String
     * @return JsonNode instance
     * @throws IOException if something wrong happened with server
     */
    private JsonNode parseJson(String json) throws IOException {
        return mapper.readTree(json);
    }

    /**
     * This method converts the given parsed values to Json-object translator can read.
     *
     * @param source text to be translated
     * @return Json-object as String
     * @throws IOException if something wrong happened with server
     * @see ParserNode
     */
    private String convertToJsonAsString(String source) throws IOException {
        ObjectNode node = mapper.createObjectNode();
        node.put(JSON_TEXT_NODE, source);
        return String.format("[%s]", mapper.writeValueAsString(node));
    }
}