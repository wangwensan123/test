package sm;


import java.io.*;
import java.util.Arrays;

public class FileEncryptUtil {

    public static void main(String[] args){

        try {
            byte[] key = SM4Util.KEY;
            byte[] iv = SM4Util.IV;
            String path = "C:\\Users\\86188\\Desktop\\client\\aaaa.txt";

            //file to bytes
            byte[] sourceBytes = FileEncryptUtil.fileToBytes(path);
            System.out.println("sourceBytes:\n" + Arrays.toString(sourceBytes));
            System.out.println(new String(sourceBytes,"UTF-8"));

            byte[] cipherText = bytesEncryptBySM4_CBC(sourceBytes);
            System.out.println("SM4 CBC Padding encrypt result:\n" + Arrays.toString(cipherText));
            System.out.println(new String(cipherText,"UTF-8"));

            byte[] decryptedData = bytesDecryptBySM4_CBC(cipherText);
            System.out.println("SM4 CBC Padding decrypt result:\n" + Arrays.toString(decryptedData));
            System.out.println(new String(decryptedData,"UTF-8"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static byte[] bytesEncryptBySM4_CBC(byte[] sourceBytes){
        try {
            return SM4Util.encrypt_CBC_Padding(SM4Util.KEY, SM4Util.IV, sourceBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static byte[] bytesDecryptBySM4_CBC(byte[] encryptedData){
        try {
            return SM4Util.decrypt_CBC_Padding(SM4Util.KEY, SM4Util.IV, encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * File转Bytes
     * @param path
     * @return byte[]
     */
    public static byte[] fileToBytes(String path) {
        byte[] buffer = null;
        try {
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int n;
            while ((n = fileInputStream.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, n);
            }
            fileInputStream.close();
            byteArrayOutputStream.close();
            buffer = byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * File转Bytes
     * @return byte[]
     */
    public static void bytesToFile(byte[] byteArray, String targetPath) {
        InputStream in = new ByteArrayInputStream(byteArray);
        File file = new File(targetPath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
