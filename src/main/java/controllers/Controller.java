package controllers;

/**
 * Controllers in this application implement this interface.
 * <p>
 * Controller is a class, which unites
 * View representation and Model data.
 *
 * @author dmfrpro
 */
public interface Controller {
    /**
     * This method initializes controller. All requirements are:
     * <p>
     * 1. Initialize View and Model
     * 2. Load View's interface
     * 3. Load saved metafiles and properties
     */
    void init();

    /**
     * This method must contain a main logic. All requirements are:
     * <p>
     * 1. Choose file or directory to translate
     * 2. Choose the path of translated files
     * <p>
     * 3. Read original file to OriginalMetaFile object and Parser
     *
     * @see models.OriginalMetaFile
     * @see utilities.parsers.Parser
     * <p>
     * 4. Translate OriginalMetaFile object to TranslatedMetaFile using Translator
     * @see models.TranslatedMetaFile
     * @see utilities.translators.Translator
     * <p>
     * 5. Serialize OriginalMetaFile to selected directory
     * 6. Check if original file was changed:
     * 6.1 If it true, change all data in translated files
     * 6.2 Else just skip this step
     */
    void translateFiles();

    /**
     * This method saves and terminates controller. All requirements are:
     * <p>
     * 1. Close View's interface
     * 2. Serialize all metafiles to selected directory
     * 3. Save all properties
     * 4. Exit the program
     */
    void exit();
}
