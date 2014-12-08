package dilu.kxq.tools.inst;

import dilu.kxq.tools.inst.helper.DebugHelper;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

/**
 * Created with IntelliJ IDEA.
 * User: dilu.kxq
 * Date: 14-10-15
 * Time: 下午8:52
 * To change this template use File | Settings | File Templates.
 */
public class InstrumentCrow {

    public static void premain(String agentArgs, Instrumentation inst) {
        agentmain(agentArgs, inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        try {

            JarFile jarFile = new JarFile(agentArgs);
            if(!inst.isNativeMethodPrefixSupported()){
                System.out.println("NativeMethodPrefix Unsupported, exit ");
                return ;
            }
            inst.appendToBootstrapClassLoaderSearch(jarFile);
            ClassFileTransformer trans = new NativeMethodStackTrans();
            inst.addTransformer(trans, true);
            inst.setNativeMethodPrefix(trans,"dilu_native_trans_");
            Class[] clses = inst.getAllLoadedClasses();
            for (Class cls : clses) {
                boolean isModifiable = inst.isModifiableClass(cls);
                if (isModifiable && cls.getName().equals("sun.misc.Unsafe")) {
//                if (isModifiable && cls.getName().equals("base.dilu.kxq.mem.TestHelperClass")) {
                    DebugHelper.info("retrans ", cls.getName());
                    inst.retransformClasses(cls);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}


