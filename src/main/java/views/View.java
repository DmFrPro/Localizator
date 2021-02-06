package views;

/**
 * Views in this application implement this interface.
 * <p>
 * View is a class, which observes and represents user interface.
 * This may be Swing GUI, Console dialog or HTML page.
 *
 * @author dmfrpro
 */
public interface View {
    /**
     * This method initializes user interface. All requirements are:
     * <p>
     * 1. Initialize all components
     * 3. Load saved selected values from properties
     */
    void init();

    /**
     * This method saves and terminates user interface. All requirements are:
     * <p>
     * 1. Save all user's selected options to properties
     * 4. Close user interface
     */
    void close();
}
