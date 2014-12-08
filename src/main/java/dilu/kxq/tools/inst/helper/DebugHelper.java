package dilu.kxq.tools.inst.helper;

/**
 * Created with IntelliJ IDEA.
 * User: dilu.kxq
 * Date: 14-10-21
 * Time: 下午3:00
 * To change this template use File | Settings | File Templates.
 */
public class DebugHelper {
    private static final String osName = System.getProperty("os.name");

    public static boolean isDebug() {
        if (osName.startsWith("Windows"))
            return true;
        return false;
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        System.out.println(isDebug());
    }

    public static void debug(String... msgs) {
        if (isDebug())
            info(msgs);
    }

    public static void info(String... msgs) {
        StringBuilder sb = new StringBuilder(msgs.length * 10);
        for (String s : msgs)
            sb.append(s);
        System.out.println(sb);
    }
}
