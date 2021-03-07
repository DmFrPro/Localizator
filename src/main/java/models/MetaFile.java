package models;

import utilities.parsers.ParserNode;

import java.nio.file.Path;
import java.util.List;

/**
 * Metafiles in this application store a meta data of the files.
 * For example, parsed values and translated values
 *
 * @author dmfrpro
 */
public interface MetaFile {

    /**
     * File's path getter.
     *
     * @return path of the file
     */
    Path getPath();

    /**
     * File's language.
     *
     * @return language suffix as String
     */
    String getLanguage();

    /**
     * File's content getter.
     *
     * @return file's content as String
     */
    String getFileContent();

    /**
     * File's parsed values.
     *
     * @return list with parsed values
     * @see utilities.parsers.ParserNode
     */
    List<ParserNode> getParsedValues();
}
