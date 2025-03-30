package info.kgeorgiy.ja.Gusarov.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * Utility class for handling JAR file operations and Java compilation.
 * Provides functionality for compiling Java files and creating JAR archives.
 */
public class JarProcessor {
    /**
     * Compiles the specified Java file using the system Java compiler.
     * Uses UTF-8 encoding and includes the classpath of the specified token.
     *
     * @param javaFile path to the Java file to compile
     * @param token class token for classpath resolution
     * @throws ImplerException if compilation fails or classpath cannot be determined
     */
    static void compile(Path javaFile, Class<?> token) throws ImplerException {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        String classpath;
        try {
            classpath = Path.of(token.getProtectionDomain().getCodeSource().getLocation().toURI())
                    + File.pathSeparator + System.getProperty("java.class.path");
        } catch (URISyntaxException e) {
            throw new ImplerException("Error getting classpath", e);
        }

        final int exitCode = compiler.run(null, null, null, "-cp",
                classpath,
                "-encoding",
                StandardCharsets.UTF_8.name(),
                javaFile.toString());
        if (exitCode != 0) {
            throw new ImplerException("Compilation failed");
        }
    }

    /**
     * Creates a JAR file containing the compiled class file.
     * The class file is added to the JAR with the appropriate package structure.
     *
     * @param jarFile path where the JAR file should be created
     * @param tempDir directory containing the compiled class file
     * @param token class token for determining the class file path
     * @throws ImplerException if JAR file creation fails
     */
    static void classToJar(Path jarFile, Path tempDir, Class<?> token) throws ImplerException {
        String className = token.getPackageName().replace('.', '/') + "/" + token.getSimpleName() + "Impl.class";
        Path classFile = tempDir.resolve(className);

        try (JarOutputStream jarOut = new JarOutputStream(Files.newOutputStream(jarFile), new Manifest())) {
            jarOut.putNextEntry(new JarEntry(className));
            Files.copy(classFile, jarOut);
        } catch (IOException e) {
            throw new ImplerException("Failed to create jar file: " + e.getMessage());
        }
    }
}