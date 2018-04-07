package crawler;
/**
 *@auth wws
 *@date 2018年4月5日---下午11:42:12
 *
 **/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HTTPUtils {
  
          //这个方法是向后台请求数据，获取html或者json等
    public static String  getRawHtml(String personalUrl) throws InterruptedException,IOException {
        URL url = new URL(personalUrl);
        URLConnection conn = url.openConnection();
        InputStream in=null;
        try {
            conn.setConnectTimeout(3000);
            in = conn.getInputStream();
        } catch (Exception e) {
                    }
        String html = convertStreamToString(in);
        return html;
    }
          //这个方法是将InputStream转化为String
    public static String convertStreamToString(InputStream is) throws IOException {
        if (is == null)
            return "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"gbk"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reader.close();
        return sb.toString();

    }
}
