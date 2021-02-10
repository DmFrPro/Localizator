package constants;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This interface contains only constant variables
 *
 * @author dmfrpro
 */
public interface Constants {
    Path PROJECT_PATH = Paths.get(
            Constants.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath()
                    .replace("/target/classes", "")
    );
}
