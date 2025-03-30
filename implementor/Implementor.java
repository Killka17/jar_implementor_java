//package info.kgeorgiy.ja.Gusarov.implementor;
//
//import info.kgeorgiy.java.advanced.implementor.Impler;
//import info.kgeorgiy.java.advanced.implementor.ImplerException;
//import info.kgeorgiy.java.advanced.implementor.tools.JarImpler;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardOpenOption;
//import java.util.Objects;
//
///**
// * The main class of java interface implementor.
// * Implementation of {@link Impler} and {@link JarImpler} interfaces that generates implementations of interfaces.
// * The generated implementations are placed in the specified directory or JAR file.
// *
// * @author Gusarov
// */
//public class Implementor implements Impler, JarImpler {
//    /**
//     * Generates implementation of the interface.
//     * The implementation is placed in the specified root directory.
//     *
//     * @param token interface to implement
//     * @param root root directory for the implementation
//     * @throws ImplerException if implementation cannot be generated
//     */
//    @Override
//    public void implement(Class<?> token, Path root) throws ImplerException {
//        if (!token.isInterface()) {
//            throw new ImplerException("It is necessary to transmit the interface");
//        }
//        Path filePath = root.resolve(token.getPackageName().replace('.', File.separatorChar))
//                .resolve(token.getSimpleName() + "Impl.java");
//
//        try {
//            Files.createDirectories(filePath.getParent());
//            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
//                HeaderGenerator.generateHeader(writer, token);
//                ClassGenerator.generateClass(writer, token);
//            }
//        } catch (IOException e) {
//            throw new ImplerException("Error writing file: " + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * Generates implementation of the interface and packages it into a JAR file.
//     *
//     * @param token interface to implement
//     * @param jarFile target JAR file
//     * @throws ImplerException if implementation cannot be generated or packaged
//     */
//    @Override
//    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
//        Path tempDir;
//        try {
//            tempDir = Files.createTempDirectory("implTemp");
//        } catch (IOException e) {
//            throw new ImplerException("Can't create temp directory: " + e.getMessage());
//        }
//
//        implement(token, tempDir);
//
//        Path filePath = tempDir.resolve(token.getPackageName().replace('.', File.separatorChar))
//                .resolve(token.getSimpleName() + "Impl.java");
//
//        JarProcessor.compile(filePath, token);
//
//        JarProcessor.classToJar(jarFile, tempDir, token);
//    }
//
//    /**
//     * Main method for command-line usage.
//     * Usage: java Implementor -jar className jarFile
//     *
//     * @param args command line arguments
//     */
//    public static void main(String[] args) {
//        if (args == null || args.length != 3 || !Objects.equals(args[0], "-jar")) {
//            System.err.println("Usage: java Implementor -jar className jarFile");
//            return;
//        }
//
//        try {
//            Implementor implementor = new Implementor();
//            try {
//                Class<?> token = Class.forName(args[1]);
//                implementor.implementJar(token, Path.of(args[2]));
//            } catch (ClassNotFoundException e) {
//                System.err.println("Class not found: " + args[1]);
//            }
//
//        } catch (ImplerException e) {
//            System.err.println("Error: " + e.getMessage());
//        }
//    }
//}