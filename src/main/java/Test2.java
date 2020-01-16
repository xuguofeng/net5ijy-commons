import java.io.File;
import org.net5ijy.commons.util.encrypt.MD5Util;

public class Test2 {

  public static void main(String[] args) throws Exception {

    long start = System.currentTimeMillis();
//    String md5 = MD5Util.getMD5(new File("D:/soft01/CentOS-7-x86_64-DVD-1708.iso"));
    String md5 = MD5Util.getMD5(new File("D:/appstore_20200109102100.apk"));
    System.out.println(md5);
    System.out.println(System.currentTimeMillis() - start);
  }
}
