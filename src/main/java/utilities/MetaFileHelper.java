package utilities;

import models.OriginalMetaFile;
import models.TranslatedMetaFile;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

/**
 * This utilitarian class contains useful methods
 * for working with metafiles.
 *
 * @author dmfrpro
 */
public final class MetaFileHelper {

    /**
     * Utilitarian classes must have private constructors
     * to prevent creation instances.
     */
    private MetaFileHelper() {
    }

    /**
     * This method reads file from given path and returns his content as string.
     *
     * @param path file's path
     * @return file's content as one string
     */
    public static @NotNull String readFileContent(Path path) {
        return null;
    }

    /**
     * This method writes original metafile.
     *
     * @param originalMetaFile original metafile instance
     */
    public static void writeOriginalMetaFile(@NotNull OriginalMetaFile originalMetaFile) {
    }

    /**
     * This method writes translated metafile.
     *
     * @param translatedMetaFile translated metafile instance
     */
    public static void writeTranslatedMetaFile(@NotNull TranslatedMetaFile translatedMetaFile) {
    }

    /**
     * This method serializes OriginalMetaFile to given path.
     *
     * @param path             file's path where to serialize
     * @param originalMetaFile OriginalMetaFile instance of the file
     * @see OriginalMetaFile
     */
    public static void saveOriginalMetaFile(@NotNull Path path, @NotNull OriginalMetaFile originalMetaFile) {
    }

    /**
     * This method deserializes OriginalMetaFile from given path.
     *
     * @param path file's path where OriginalMetaFile locates
     * @return restored OriginalMetaFile's instance
     * @see OriginalMetaFile
     */
    public static @NotNull OriginalMetaFile loadOriginalMetaFile(@NotNull Path path) {
        return null;
    }
}
