package socket;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SSLServer {

    public static void startSSLServer() throws IOException {
        int port = 15408;//监听端口
        String keyFile = "C:\\Users\\86188\\Desktop\\transfer\\certs\\client.crt";//密钥库文件
        String keyFilePass = "sslkey1";//密钥库的密码
        String keyPass = "sslkey1";//密钥别名的密码
        SSLServerSocket sslsocket = null;//安全连接套接字
        KeyStore ks;//密钥库
        KeyManagerFactory kmf;//密钥管理工厂
        SSLContext sslc = null;//安全连接方式
        //初始化安全连接的密钥
        try {
            ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(keyFile), keyFilePass.toCharArray());
            kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, keyPass.toCharArray());
            sslc = SSLContext.getInstance("SSLv3");
            sslc.init(kmf.getKeyManagers(), null, null);
        } catch (KeyManagementException ex) {
            Logger.getLogger(SSLServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(SSLServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(SSLServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SSLServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(SSLServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        //用安全连接的工厂来创建安全连接套接字
        SSLServerSocketFactory sslssf = sslc.getServerSocketFactory();
        sslsocket = (SSLServerSocket) sslssf.createServerSocket(port);//创建并进入监听
        System.out.println("Listening...");
        SSLSocket ssocket = (SSLSocket) sslsocket.accept();//接受客户端的连接
        System.out.println("Server Connection OK~");
        System.out.println("========================");
        System.out.println("");
        //以下代码同socket通讯实例中的代码
        BufferedReader socketIn = new BufferedReader(new InputStreamReader(ssocket.getInputStream()));
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        PrintStream socketOut = new PrintStream(ssocket.getOutputStream());
        String s;
        while (true) {
            System.out.println("Please wait client 's message..");
            System.out.println("");
            s = socketIn.readLine();
            System.out.println("Client Message: " + s);
            if (s.trim().equals("BYE")) break;
            System.out.print("Server Message: ");
            s = userIn.readLine();
            socketOut.println(s);
            if (s.trim().equals("BYE")) break;
        }
        socketIn.close();
        socketOut.close();
        userIn.close();
        sslsocket.close();
    }

    public static void main(String[] args) {
        try {
            startSSLServer();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
