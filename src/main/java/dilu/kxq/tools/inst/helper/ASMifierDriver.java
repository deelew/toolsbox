package dilu.kxq.tools.inst.helper;

import org.objectweb.asm.util.ASMifier;

/**
 * Created with IntelliJ IDEA.
 * User: dilu.kxq
 * Date: 14-10-16
 * Time: 上午11:04
 * To change this template use File | Settings | File Templates.
 */
public class ASMifierDriver {
    public static void main(String[] args) throws Exception {
        String targetClass = "dilu.kxq.tools.inst.NativeMethodStackTrans" ;
//        targetClass = "sun.misc.Unsafe";
        ASMifier.main(new String[]{targetClass});
    }
}
