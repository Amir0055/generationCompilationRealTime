package com.example.generationclassasm.Labo;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class DynamicClassGenerator {
    public static void main(String[] args) {
        try {
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

            // Define class metadata
            classWriter.visit(Opcodes.V17, Opcodes.ACC_PUBLIC, "mouna3", null,
                    "io/gatling/javaapi/core/Simulation", null);

            // Create run() method
            MethodVisitor runMethodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "run", "()V", null, null);
            runMethodVisitor.visitCode();
            // Create HttpProtocolBuilder instance and chain methods

            // Configure HttpProtocolBuilder from HttpDsl.http
            runMethodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "io/gatling/javaapi/http/HttpDsl", "http", "Lio/gatling/javaapi/http/HttpProtocolBuilder;");
            runMethodVisitor.visitLdcInsn("http://localhost:8080");
            runMethodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "io/gatling/javaapi/http/HttpProtocolBuilder", "baseUrl", "(Ljava/lang/String;)Lio/gatling/javaapi/http/HttpProtocolBuilder;", false);
            runMethodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "io/gatling/javaapi/http/HttpProtocolBuilder", "inferHtmlResources", "()Lio/gatling/javaapi/http/HttpProtocolBuilder;", false);
            runMethodVisitor.visitLdcInsn("application/json");
            runMethodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "io/gatling/javaapi/http/HttpProtocolBuilder", "acceptHeader", "(Ljava/lang/String;)Lio/gatling/javaapi/http/HttpProtocolBuilder;", false);
            runMethodVisitor.visitLdcInsn("Gatling/Performance Test");
            runMethodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "io/gatling/javaapi/http/HttpProtocolBuilder", "userAgentHeader", "(Ljava/lang/String;)Lio/gatling/javaapi/http/HttpProtocolBuilder;", false);

            // Rest of your code here
            // Example: Add HttpProtocolBuilder field
            runMethodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
            runMethodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "io/gatling/javaapi/http/HttpDsl", "http", "Lio/gatling/javaapi/http/HttpProtocolBuilder;");
            runMethodVisitor.visitFieldInsn(Opcodes.PUTFIELD, "CustomerRequestSimulation", "httpProtocol", "Lio/gatling/javaapi/http/HttpProtocolBuilder;");

            // End of the run() method
            runMethodVisitor.visitInsn(Opcodes.RETURN);
            runMethodVisitor.visitMaxs(2, 1);
            runMethodVisitor.visitEnd();

            // Finish class creation
            classWriter.visitEnd();

            // Get the generated bytecode as a byte array
            byte[] classBytes = classWriter.toByteArray();

            // Save the bytecode as a .class file
            saveToFile("MounAa.class", classBytes);

            System.out.println("Generated class saved as CustomerRequestSimulation.class");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveToFile(String fileName, byte[] content) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(content);
        }
    }
}
