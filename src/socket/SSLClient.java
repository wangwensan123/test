package socket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLClient {
    private SSLSocket s;
    private DataOutputStream out;
    private DataInputStream in;

    public SSLClient() throws IOException {
    }

    public void sendMessage(Socket s, String msg) {
        try {

            out = new DataOutputStream(s.getOutputStream());
            String jsonString = msg;
            out.write(jsonString.getBytes());
        } catch (IOException e) {
        }

    }

    public String receiveMessage(Socket s) {
        try {

            in = new DataInputStream(s.getInputStream());
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(in));// 接受到
            String res = socketIn.readLine();
            if (res == null || res.equals("null")) {
                res = "";
            }
            return res;
        } catch (Exception e) {
            return "";
        }

    }

    public String talk(String ip, int port, String trustStore, String trustStorePassword, int socketOutTime, String msg)
            throws Exception {
        try {
            int connectTimeout = socketOutTime * 1000;//设置超时时间
            System.setProperty("javax.net.ssl.trustStore", trustStore);// 设置可信任的密钥仓库
            System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword); // 设置可信任的密钥仓库的密码
            SSLSocketFactory sslsf = (SSLSocketFactory) SSLSocketFactory.getDefault();// 利用工厂来创建SSLSocket安全套接字
            s = (SSLSocket) sslsf.createSocket();
            s.connect(new InetSocketAddress(ip, port), connectTimeout);
            s.startHandshake();

            System.out.println("向服务器发送:" + msg);
            sendMessage(s, msg);
            // 发字符串
            String result = receiveMessage(s);
            System.out.println("服务器返回:" + result);
            out.close();
            in.close();
            if (s != null) {
                s.close();
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            out.close();
            in.close();
            if (s != null) {
                s.close();
            }
            return "";
        }

    }

    public static void main(String[] args) throws Exception {
        SSLClient c = new SSLClient();
        String responseMsg = c.talk("192.168.0.8", 8089, "C:\\Users\\86188\\Desktop\\transfer\\certs\\certs\\client.crt", "hxwa", 5, "test\n");
        System.out.println(responseMsg);

    }

}
