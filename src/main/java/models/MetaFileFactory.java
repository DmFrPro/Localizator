package models;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import translators.TranslatorNode;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This utilitarian class contains useful methods
 * for working with metafiles.
 *
 * @author dmfrpro
 */
public final class MetaFileFactory {

    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(MetaFileFactory.class);

    /**
     * Utilitarian classes must have private constructors
     * to prevent creation instances.
     */
    private MetaFileFactory() {
    }

    /**
     * This method returns new original metafile instance.
     *
     * @param path     file's path
     * @param language file's language
     * @return OriginalMetaFile instance
     * @throws Exception I/O or Parser exceptions
     */
    public static MetaFile newOriginalMetaFile(
            @NotNull Path path,
            @NotNull String language
    ) throws Exception {
        return OriginalMetaFile.newInstance(path, language);
    }

    /**
     * This method returns new translated metafile instance.
     *
     * @param originalMetaFile original metafile
     * @param language         file's language
     * @param translatedValues list with translated values
     * @return OriginalMetaFile instance
     */
    public static MetaFile newTranslatedMetaFile(
            @NotNull MetaFile originalMetaFile,
            @NotNull String language,
            @NotNull List<TranslatorNode> translatedValues
    ) {
        return TranslatedMetaFile.newInstance(originalMetaFile, language, translatedValues);
    }

    /**
     * This method reads file from given path and returns his content as string.
     *
     * @param path file's path
     * @return file's content as one string
     */
    public static @NotNull String readFileContent(Path path)
            throws IOException {

        logger.info("Started reading file from path " + path.toString());

        if (path.toFile().isDirectory()) {
            logger.error("Given path for method readFileContent() is not the file");
            throw new IllegalArgumentException("Given path is not the file");
        }

        BufferedReader reader = new BufferedReader(new FileReader(path.toString()));
        String line = reader.lines().collect(Collectors.joining("\n"));
        reader.close();

        logger.info("Successfully read file from path " + path.toString());

        return line;
    }

    /**
     * This method writes translated metafile.
     *
     * @param translatedMetaFile translated metafile instance
     */
    public static void writeTranslatedMetaFile(@NotNull TranslatedMetaFile translatedMetaFile)
            throws IOException {

        logger.info("Started writing translated metafile to path " + translatedMetaFile.getPath().toString());

        BufferedWriter writer = new BufferedWriter(new FileWriter(translatedMetaFile.getPath().toString()));
        writer.write(translatedMetaFile.getFileContent());
        writer.close();

        logger.info("Successfully wrote translated file to path " + translatedMetaFile.getPath().toString());
    }

    /**
     * This method returns new path with inserted language suffix.
     * <p>
     * For example:
     * path = /.../res/values/file.xml
     * language = "ru"
     * <p>
     * This method will return:
     * /.../res/values-ru/file.xml
     *
     * @param path     current path
     * @param language language suffix as String
     * @return new path with inserted language suffix
     */
    public static Path getPathWithLanguageSuffix(Path path, String language) {

        if (path.toFile().isDirectory()) {
            logger.error("Given path for method getPathWithLanguageSuffix() is not the file");
            throw new IllegalArgumentException("Given path is not the file");
        }

        return Paths.get(
                String.format(
                        "%s-%s/%s",
                        path.getParent().toAbsolutePath().toString(),
                        language,
                        path.getFileName().toString()
                )
        );
    }
}
