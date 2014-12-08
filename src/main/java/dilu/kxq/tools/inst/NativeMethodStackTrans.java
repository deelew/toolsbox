package dilu.kxq.tools.inst;

import org.objectweb.asm.*;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import static com.sun.xml.internal.ws.org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static com.sun.xml.internal.ws.org.objectweb.asm.ClassWriter.COMPUTE_MAXS;
import static org.objectweb.asm.Opcodes.*;

/**
 * User: dilu.kxq
 * Date: 14-12-8
 */
public class NativeMethodStackTrans implements ClassFileTransformer {
    public static void main(String[] args) {
        System.out.println((COMPUTE_FRAMES | COMPUTE_MAXS) & COMPUTE_FRAMES);
        System.out.println((COMPUTE_FRAMES | COMPUTE_MAXS) & COMPUTE_MAXS);
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!className.equals("1") )
//        if (!className.equals("base/dilu/kxq/mem/TestHelperClass"))
            return classfileBuffer;
        try {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(COMPUTE_FRAMES);
            ClassVisitor cv = new TraceClassVisitor(cw, new PrintWriter(System.out));
            cv = new ClassVisitor(Opcodes.ASM4, cv) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    if (!name.equals("allocateMemory")) {
                        return cv.visitMethod(access, name, desc, signature, exceptions);
                    } else {
                        String _name = "dilu_native_trans_allocateMemory";
                        String _desc = "(J)J";
                        MethodVisitor mv0 = cv.visitMethod(ACC_PUBLIC|ACC_NATIVE, _name, _desc, null, null);
//                        mv0.visitCode();
//                        mv0.visitInsn(LCONST_1);
//                        mv0.visitInsn(LRETURN);
//                        mv0.visitMaxs(0,0);
                        mv0.visitEnd();
                        MethodVisitor mv = cv.visitMethod(access& ~ACC_NATIVE, name, desc, signature, exceptions);
                        if (mv != null) {

                            mv = new MethodVisitor(Opcodes.ASM4, mv) {
                                @Override
                                public void visitCode() {
                                    mv.visitCode();
                                    mv.visitTypeInsn(NEW, "java/lang/Throwable");
                                    mv.visitInsn(DUP);
                                    mv.visitLdcInsn("catchStack");
                                    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Throwable", "<init>", "(Ljava/lang/String;)V");
                                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Throwable", "printStackTrace", "()V");
                                    mv.visitVarInsn(ALOAD, 0);
                                    mv.visitVarInsn(LLOAD, 1);
//                                    mv.visitMethodInsn(INVOKEVIRTUAL, "base/dilu/kxq/mem/TestHelperClass", "dilu_native_trans_allocateMemory", "(J)J");
                                    mv.visitMethodInsn(INVOKEVIRTUAL, "sun/misc/Unsafe", "dilu_native_trans_allocateMemory", "(J)J");
                                    mv.visitInsn(LRETURN);
                                }

                                @Override
                                public void visitEnd() {
                                    this.visitCode();
                                    mv.visitMaxs(0,0);
                                    super.visitEnd();
                                }
                            };
                        }
                        return mv;
                    }
                }

                @Override
                public void visitEnd() {
//                    String name = "dilu_native_trans_allocateMemory";
//                    String desc = "(J)J";
//                    MethodVisitor mv = cv.visitMethod(ACC_PUBLIC|ACC_NATIVE, name, desc, null, null);
//                    mv.visitCode();
//                    mv.visitEnd();
                    super.visitEnd();
                }
            };
            cv = new TraceClassVisitor(cv, new PrintWriter(System.out));
            cr.accept(cv, 0);
            return cw.toByteArray();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return classfileBuffer;
    }

    int code(int i) {
        new Throwable("catchStack").printStackTrace();
//        throw new RuntimeException("");
//        return allocat(i);
        return 0 ;
    }

    private void allocat(int i) {
//        Runtime.getRuntime().gc();
//        ByteBuffer bf = ByteBuffer.allocateDirect(10000);
//        return i;
    }
}
