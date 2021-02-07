package models;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Map;

/**
 * This singleton class stores original metafiles.
 *
 * @author dmfrpro
 */
public class OriginalMetaFileStorage {

    /**
     * Singleton instance
     */
    private static final @NotNull OriginalMetaFileStorage instance = new OriginalMetaFileStorage();

    /**
     * This map stores original metafiles.
     * <p>
     * Map data:
     * 1. Keys are paths to original files
     * 2. Values are OriginalMetaFile instances of files.
     * <p>
     * This field is initialized automatically by
     * loadStorage() method.
     *
     * @see OriginalMetaFile
     */
    private final @NotNull Map<Path, OriginalMetaFile> storage;

    /**
     * Singleton classes must have private constructors.
     * <p>
     * This constructor initializes storage variable by
     * loadStorage() method.
     */
    private @NotNull OriginalMetaFileStorage() {
        this.storage = loadStorage();
    }

    /**
     * This method gets an singleton instance of this class.
     * @return OriginalMetaFileStorage instance
     */
    public static @NotNull OriginalMetaFileStorage getInstance() {
        return instance;
    }

    /**
     * Storage getter.
     *
     * Map data:
     * 1. Keys are paths to original files
     * 2. Values are OriginalMetaFile instances of files.
     *
     * @return Map with paths and original metafiles
     */
    public @NotNull Map<Path, OriginalMetaFile> getStorage() {
        return storage;
    }

    /**
     * This method loads serialized original metafiles from filesystem.
     *
     * Map data:
     * 1. Keys are paths to original files
     * 2. Values are OriginalMetaFile instances of files.
     *
     * @return Map with paths and original metafiles
     */
    private @NotNull Map<Path, OriginalMetaFile> loadStorage() {
        return null;
    }
}
