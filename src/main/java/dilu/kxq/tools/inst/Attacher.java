package dilu.kxq.tools.inst;

import com.sun.tools.attach.VirtualMachine;

/**
 * import com.sun.tools.attach.VirtualMachine;
 * <p/>
 * /**
 * Simple attach-on-demand client tool that
 * loads the given agent into the given Java process.
 */
public class Attacher {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("usage: java Attach <pid> <agent-jar-full-path> [<agent-args>]");
            System.exit(1);
        }

// JVM is identified by process id (pid).
        VirtualMachine vm = VirtualMachine.attach(args[0]);
        String agentArgs = (args.length > 2) ? args[2] : null;
// load a specified agent onto the JVM
        String agent = args[1];
        String options = args[1];
        System.out.println("agent " + agent + ",options " + options);
        vm.loadAgent(agent, options);
        System.out.println("Attach done!");
    }
}