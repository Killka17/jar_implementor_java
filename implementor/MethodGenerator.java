package info.kgeorgiy.ja.Gusarov.implementor;

import java.lang.reflect.Method;

/**
 * Utility class for generating method implementations.
 * Handles the generation of method signatures and default return values.
 */
public class MethodGenerator {
    /**
     * Generates implementation of the specified method.
     * The generated method has the same signature as the original method
     * and returns a default value based on the return type.
     *
     * @param method method to implement
     * @return string containing the generated method implementation
     */
    public static String generateMethod(Method method) {
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public ").append(method.getReturnType().getCanonicalName()).append(" ").append(method.getName()).append("(");

        Class<?>[] params = method.getParameterTypes();
        for (int i = 0; i < params.length; i++) {
            sb.append(params[i].getCanonicalName()).append(" arg").append(i);
            if (i < params.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");

        Class<?>[] exceptionsArr = method.getExceptionTypes();
        if (exceptionsArr.length > 0) {
            sb.append(" throws ");
            for (int i = 0; i < exceptionsArr.length; i++) {
                sb.append(exceptionsArr[i].getCanonicalName());
                if (i < exceptionsArr.length - 1) {
                    sb.append(", ");
                }
            }
        }
        sb.append(" {\n");

        sb.append("        return ").append(getDefaultValue(method.getReturnType())).append(";\n    }");

        return sb.toString();
    }

    /**
     * Returns a default value for the specified return type.
     *
     * @param returnType the return type of the method
     * @return string representation of the default value
     */
    private static String getDefaultValue(Class<?> returnType) {
        if (returnType == boolean.class) return "false";
        else if (!returnType.isPrimitive()) return "null";
        else if (returnType == void.class) return "";
        return "0";
    }
}
