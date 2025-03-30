package info.kgeorgiy.ja.Gusarov.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Utility class for generating class implementations.
 * Handles the generation of class structure and method implementations.
 */
public class ClassGenerator {
    /**
     * Generates implementation class for the specified interface.
     * The generated class implements all abstract methods of the interface.
     *
     * @param writer writer to write the generated code
     * @param token interface to implement
     * @throws ImplerException if implementation cannot be generated
     * @throws IOException if an I/O error occurs while writing
     */
    public static void generateClass(BufferedWriter writer, Class<?> token) throws ImplerException, IOException {
        if (Modifier.isPrivate(token.getModifiers())) {
            throw new ImplerException("Cannot implement private interface");
        }
        writer.write("public class " + token.getSimpleName() + "Impl implements " + token.getCanonicalName() + " { \n");
        for (Method method : token.getMethods()) {
            if (Modifier.isAbstract(method.getModifiers())) {
                writer.write(MethodGenerator.generateMethod(method));
            }
        }
        writer.write("}");
    }
}
