package info.kgeorgiy.ja.Gusarov.implementor;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Utility class for generating package declarations in Java source files.
 */
public class HeaderGenerator {
    /**
     * Generates package declaration for the specified class.
     * If the class is in the default package (empty package name), no declaration is generated.
     *
     * @param writer writer to write the package declaration
     * @param token class token for determining the package
     * @throws IOException if an I/O error occurs while writing
     */
    public static void generateHeader(BufferedWriter writer, Class<?> token) throws IOException {
        if (!token.getPackageName().isEmpty()) {
            writer.write("package " + token.getPackageName() + ";\n");
        }
    }
}
