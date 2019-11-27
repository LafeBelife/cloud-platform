package cn.wbw.test;

import org.junit.Test;
import org.springframework.http.HttpMethod;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * 测试
 *
 * @author wbw
 * @date 2019-11-23 16:42
 */
public class DsClientTest {
    @Test
    public void test01() {
        String uri = "http://localhost:8891/webInfoSJService";
        String mess = "{'name':'alibaba'}";
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(HttpMethod.POST.name());
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setAllowUserInteraction(true);

            conn.setUseCaches(false);
            conn.setReadTimeout(10*1000);
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
            conn.setRequestProperty("Content-Type","application/json");
            conn.connect();
            OutputStream outputStream = conn.getOutputStream();
            BufferedOutputStream out = new BufferedOutputStream(outputStream);
            out.write(mess.getBytes());
            out.flush();
            StringBuilder temp = new StringBuilder();
            InputStream inputStream = conn.getInputStream();
            byte[] bs = new byte[1024];
            while (inputStream.read(bs,0,1024) != -1){
                temp.append(new String(bs));
            }
            System.out.println(conn.getResponseCode());
            System.out.println(temp.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
