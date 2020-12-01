package sm;

import sm.cert.GMBase;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.macs.GMac;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.util.Arrays;

public class SM4Util extends GMBaseUtil {
    public static final String ALGORITHM_NAME = "SM4";
    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding";
    public static final String ALGORITHM_NAME_ECB_NOPADDING = "SM4/ECB/NoPadding";
    public static final String ALGORITHM_NAME_CBC_PADDING = "SM4/CBC/PKCS5Padding";
    public static final String ALGORITHM_NAME_CBC_NOPADDING = "SM4/CBC/NoPadding";
    public static final byte[] KEY = new byte[]{ 58, 126, -102, 5, -76, -88, 68, -46, 101, -46, 31, 35, 38, 116, -9, -78 };
    public static final byte[] IV = new byte[]{ -47, -34, 51, -126, -86, -43, 73, -33, -35, -80, -123, 76, 3, 84, -116, 16 };

    /**
     * SM4算法目前只支持128位（即密钥16字节）
     */
    public static final int DEFAULT_KEY_SIZE = 128;

    public static byte[] generateKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        return generateKey(DEFAULT_KEY_SIZE);
    }

    public static byte[] generateKey(int keySize) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
        kg.init(keySize, new SecureRandom());
        return kg.generateKey().getEncoded();
    }

    public static byte[] encrypt_ECB_Padding(byte[] key, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = generateECBCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt_ECB_Padding(byte[] key, byte[] cipherText)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
        Cipher cipher = generateECBCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    public static byte[] encrypt_ECB_NoPadding(byte[] key, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = generateECBCipher(ALGORITHM_NAME_ECB_NOPADDING, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt_ECB_NoPadding(byte[] key, byte[] cipherText)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
        Cipher cipher = generateECBCipher(ALGORITHM_NAME_ECB_NOPADDING, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    public static byte[] encrypt_CBC_Padding(byte[] key, byte[] iv, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException {
        Cipher cipher = generateCBCCipher(ALGORITHM_NAME_CBC_PADDING, Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt_CBC_Padding(byte[] key, byte[] iv, byte[] cipherText)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
            InvalidAlgorithmParameterException {
        Cipher cipher = generateCBCCipher(ALGORITHM_NAME_CBC_PADDING, Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(cipherText);
    }

    public static byte[] encrypt_CBC_NoPadding(byte[] key, byte[] iv, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException {
        Cipher cipher = generateCBCCipher(ALGORITHM_NAME_CBC_NOPADDING, Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt_CBC_NoPadding(byte[] key, byte[] iv, byte[] cipherText)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
            InvalidAlgorithmParameterException {
        Cipher cipher = generateCBCCipher(ALGORITHM_NAME_CBC_NOPADDING, Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(cipherText);
    }

    public static byte[] doCMac(byte[] key, byte[] data) throws NoSuchProviderException, NoSuchAlgorithmException,
            InvalidKeyException {
        Key keyObj = new SecretKeySpec(key, ALGORITHM_NAME);
        return doMac("SM4-CMAC", keyObj, data);
    }

    public static byte[] doGMac(byte[] key, byte[] iv, int tagLength, byte[] data) {
        org.bouncycastle.crypto.Mac mac = new GMac(new GCMBlockCipher(new SM4Engine()), tagLength * 8);
        return doMac(mac, key, iv, data);
    }

    /**
     * 默认使用PKCS7Padding/PKCS5Padding填充的CBCMAC
     *
     * @param key
     * @param iv
     * @param data
     * @return
     */
    public static byte[] doCBCMac(byte[] key, byte[] iv, byte[] data) {
        SM4Engine engine = new SM4Engine();
        org.bouncycastle.crypto.Mac mac = new CBCBlockCipherMac(engine, engine.getBlockSize() * 8, new PKCS7Padding());
        return doMac(mac, key, iv, data);
    }

    /**
     * @param key
     * @param iv
     * @param padding 可以传null，传null表示NoPadding，由调用方保证数据必须是BlockSize的整数倍
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] doCBCMac(byte[] key, byte[] iv, BlockCipherPadding padding, byte[] data) throws Exception {
        SM4Engine engine = new SM4Engine();
        if (padding == null) {
            if (data.length % engine.getBlockSize() != 0) {
                throw new Exception("if no padding, data length must be multiple of SM4 BlockSize");
            }
        }
        org.bouncycastle.crypto.Mac mac = new CBCBlockCipherMac(engine, engine.getBlockSize() * 8, padding);
        return doMac(mac, key, iv, data);
    }


    private static byte[] doMac(org.bouncycastle.crypto.Mac mac, byte[] key, byte[] iv, byte[] data) {
        CipherParameters cipherParameters = new KeyParameter(key);
        mac.init(new ParametersWithIV(cipherParameters, iv));
        mac.update(data, 0, data.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }

    private static byte[] doMac(String algorithmName, Key key, byte[] data) throws NoSuchProviderException,
            NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        mac.init(key);
        mac.update(data);
        return mac.doFinal();
    }

    private static Cipher generateECBCipher(String algorithmName, int mode, byte[] key)
            throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
            InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        cipher.init(mode, sm4Key);
        return cipher;
    }

    private static Cipher generateCBCCipher(String algorithmName, int mode, byte[] key, byte[] iv)
            throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            NoSuchProviderException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(mode, sm4Key, ivParameterSpec);
        return cipher;
    }

    public static void main(String[] args) {
        try {
            byte[] key = SM4Util.generateKey();
            System.out.println("key:\n" + Arrays.toString(key));
            byte[] iv = SM4Util.generateKey();
            System.out.println("iv:\n" + Arrays.toString(iv));
            byte[] cipherText = null;
            byte[] decryptedData = null;

            cipherText = SM4Util.encrypt_ECB_NoPadding(key, GMBase.SRC_DATA_16B);
            System.out.println("SM4 ECB NoPadding encrypt result:\n" + Arrays.toString(cipherText));
            decryptedData = SM4Util.decrypt_ECB_NoPadding(key, cipherText);
            System.out.println("SM4 ECB NoPadding decrypt result:\n" + Arrays.toString(decryptedData));


            cipherText = SM4Util.encrypt_ECB_Padding(key, GMBase.SRC_DATA);
            System.out.println("SM4 ECB Padding encrypt result:\n" + Arrays.toString(cipherText));
            decryptedData = SM4Util.decrypt_ECB_Padding(key, cipherText);
            System.out.println("SM4 ECB Padding decrypt result:\n" + Arrays.toString(decryptedData));


            cipherText = SM4Util.encrypt_CBC_Padding(key, iv, GMBase.SRC_DATA);
            System.out.println("SM4 CBC Padding encrypt result:\n" + Arrays.toString(cipherText));
            decryptedData = SM4Util.decrypt_CBC_Padding(key, iv, cipherText);
            System.out.println("SM4 CBC Padding decrypt result:\n" + Arrays.toString(decryptedData));


            cipherText = SM4Util.encrypt_CBC_NoPadding(key, iv, GMBase.SRC_DATA_16B);
            System.out.println("SM4 CBC NoPadding encrypt result:\n" + Arrays.toString(cipherText));
            decryptedData = SM4Util.decrypt_CBC_NoPadding(key, iv, cipherText);
            System.out.println("SM4 CBC NoPadding decrypt result:\n" + Arrays.toString(decryptedData));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testFileEncrypt(){

        try {
            byte[] key = SM4Util.KEY;
            System.out.println("key:\n" + Arrays.toString(key));
            byte[] iv = SM4Util.IV;
            System.out.println("iv:\n" + Arrays.toString(iv));
            byte[] cipherText = null;
            byte[] decryptedData = null;

            String path = "C:\\Users\\86188\\Desktop\\client\\e认证中心.exe";
            //file to bytes
            byte[] sourceBytes = FileEncryptUtil.fileToBytes(path);
            System.out.println(sourceBytes.length);


            cipherText = SM4Util.encrypt_CBC_Padding(key, iv, sourceBytes);
            System.out.println("SM4 CBC Padding encrypt result:\n" + Arrays.toString(cipherText));
            decryptedData = SM4Util.decrypt_CBC_Padding(key, iv, cipherText);
            System.out.println("SM4 CBC Padding decrypt result:\n" + Arrays.toString(decryptedData));


            cipherText = SM4Util.encrypt_CBC_NoPadding(key, iv, sourceBytes);
            System.out.println("SM4 CBC NoPadding encrypt result:\n" + Arrays.toString(cipherText));
            decryptedData = SM4Util.decrypt_CBC_NoPadding(key, iv, cipherText);
            System.out.println("SM4 CBC NoPadding decrypt result:\n" + Arrays.toString(decryptedData));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

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
