import java.io.*;

/**
 * User: dilu.kxq
 * Date: 14-12-4
 * Time: 上午11:57
 * 计算 /proc/[pid]/maps 每块内存的大小的工具
 */
public class MapsCalc {
    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.out.println("Two input args!, first input fileName, second output fileName");
            return;
        }
        String fileName = args[0];
        String outName = args[1];

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outName));
            String line = null;
            while ((line = br.readLine()) != null) {
                try{

                    String f0 = line.substring(0, line.indexOf('-'));
                    String f2 = line.substring(f0.length()+1,line.indexOf(' '));
                    long  start = Long.parseLong(f0,16);
                    long end = Long.parseLong(f2,16);
                    long size = end - start  ;

                    bw.write((size/1024) +"\t" + line +"\n");
                }   catch (NumberFormatException nfe){
                        bw.write("OutLongRange " + line);
                }
            }
            br.close();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
