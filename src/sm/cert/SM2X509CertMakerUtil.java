package sm.cert;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import sm.BCECUtil;
import sm.SM2Util;
import sm.cert.exception.InvalidX500NameException;
import sm.cert.model.SubjectDN;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SM2X509CertMakerUtil {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public boolean makeCertificate(Map<String, String> mapDN, String pri, String cer) {
        try {
            KeyPair subKP = SM2Util.generateKeyPair();

            X500Name subDN = SubjectDN.buildSubjectDN(mapDN);

            SM2PublicKey sm2SubPub = new SM2PublicKey(subKP.getPublic().getAlgorithm(),
                    (BCECPublicKey) subKP.getPublic());
            //创建csr
            byte[] csr = CommonUtil.createCSR(subDN, sm2SubPub, subKP.getPrivate(),
                    SM2X509CertMaker.SIGN_ALGO_SM3WITHSM2).getEncoded();

            //保存私钥pri
            savePriKey(pri, (BCECPrivateKey) subKP.getPrivate(), (BCECPublicKey) subKP.getPublic());


            //X509证书
            SM2X509CertMaker certMaker = buildCertMaker(mapDN);
            X509Certificate cert = certMaker.makeSSLEndEntityCert(csr);

            FileUtil.writeFile(cer, cert.getEncoded());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean makePemCertificate(Map<String, String> mapDN, String pri, String cer) {
        try {
            KeyPair subKP = SM2Util.generateKeyPair();

            X500Name subDN = SubjectDN.buildSubjectDN(mapDN);

            SM2PublicKey sm2SubPub = new SM2PublicKey(subKP.getPublic().getAlgorithm(),
                    (BCECPublicKey) subKP.getPublic());
            //创建csr
            byte[] csr = CommonUtil.createCSR(subDN, sm2SubPub, subKP.getPrivate(),
                    SM2X509CertMaker.SIGN_ALGO_SM3WITHSM2).getEncoded();

            //保存私钥pri
            savePriKeyPEM(pri, (BCECPrivateKey) subKP.getPrivate(), (BCECPublicKey) subKP.getPublic());

            //pem证书
            SM2X509CertMaker certMaker = buildCertMaker(mapDN);
            X509Certificate cert = certMaker.makeSSLEndEntityCert(csr);
            PublicKey pk = cert.getPublicKey();
            String pemPublickey = BCECUtil.convertECPublicKeyX509ToPEM(pk.getEncoded());
            System.out.println("PEM publicKey:" + pemPublickey);
            FileUtil.writeFile(cer, pemPublickey.getBytes());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void savePriKey(String filePath, BCECPrivateKey priKey, BCECPublicKey pubKey) throws IOException {
        ECPrivateKeyParameters priKeyParam = BCECUtil.convertPrivateKeyToParameters(priKey);
        ECPublicKeyParameters pubKeyParam = BCECUtil.convertPublicKeyToParameters(pubKey);
        byte[] derPriKey = BCECUtil.convertECPrivateKeyToSEC1(priKeyParam, pubKeyParam);
        FileUtil.writeFile(filePath, derPriKey);
    }

    public static void savePriKeyPEM(String filePath, BCECPrivateKey priKey, BCECPublicKey pubKey) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {

        String pemPriKey = BCECUtil.convertECPrivateKeyPKCS8ToPEM(priKey.getEncoded());
        FileUtil.writeFile(filePath, pemPriKey.getBytes());
    }

    public static SM2X509CertMaker buildCertMaker(Map map) throws InvalidAlgorithmParameterException,
        NoSuchAlgorithmException, NoSuchProviderException, InvalidX500NameException {
        X500Name issuerName = SubjectDN.buildRootCADN(map);
        KeyPair issKP = SM2Util.generateKeyPair();
        long certExpire = 20L * 365 * 24 * 60 * 60 * 1000; // 20年
        CertSNAllocator snAllocator = new RandomSNAllocator(); // 实际应用中可能需要使用数据库来保证证书序列号的唯一性。
        return new SM2X509CertMaker(issKP, certExpire, issuerName, snAllocator);
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

    public static void test1(){
        try {

//            String cer ="C:\\Users\\86188\\Desktop\\test\\sm2sm3\\server_cert.pubkey";
            String cer ="C:\\Users\\86188\\Desktop\\test\\sm2sm3\\server_cert.pem";
            getX509CerCate(cer);
            //私钥路径
            String key = "C:\\Users\\86188\\Desktop\\test\\sm2sm3\\server_private.key";
            //获取证书公钥
            X509Certificate cert = SM2CertUtil.getX509Certificate(cer);
            BCECPublicKey pubKey = SM2CertUtil.getBCECPublicKey(cert);
            BASE64Encoder base64Encoder=new BASE64Encoder();
            String publicKeyString = base64Encoder.encode(pubKey.getEncoded());
            System.out.println("-----------------publicKey---Hex-----------------");
            System.out.println(ByteUtils.toHexString(pubKey.getEncoded()));
            System.out.println("-----------------publicKey----Hex----------------");
            System.out.println("-----------------publicKey---base64-----------------");
            System.out.println(publicKeyString);
            System.out.println("-----------------publicKey----base64----------------");
//            String publicKeyStr = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAExcXxNgSdw/kn64D0+XyFPAF69QzT3ijhx6392ADlyA0eGBMh6VTxaLukdHZ/gjyPbwc60d5vhSO1SN0HZtJ/bw==";
//            String publicKeyStr = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEQIXOUNoONL/qdyuIBqBDwB4CECezME50528wu8AbfvgyGyLgO0Xjo4sjQJ1459MeAJ3F2hTpnp6wJDtS61HazQ==";
//            BASE64Decoder base64Decoder = new BASE64Decoder();
//            byte[] publicDecode = base64Decoder.decodeBuffer(publicKeyStr);
//            KeyFactory keyFactory = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicDecode);
//            BCECPublicKey pubKey = (BCECPublicKey)keyFactory.generatePublic(keySpec);

//            //获取证书私钥
//            String privateStr = "MHcCAQEEIO0C0SVu3kXD1p8FZ8FjO5PiC4ofw4vUcqN8jq1ffYAwoAoGCCqBHM9VAYItoUQDQgAEQIXOUNoONL/qdyuIBqBDwB4CECezME50528wu8AbfvgyGyLgO0Xjo4sjQJ1459MeAJ3F2hTpnp6wJDtS61HazQ==";
//
//            byte[] priKeyData = FileUtil.readFile(key);
//
//            KeyFactory keyFactory1 = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
//            BASE64Decoder base64Decoder = new BASE64Decoder();
//            byte[] buffer = base64Decoder.decodeBuffer(privateStr);
//            PKCS8EncodedKeySpec keySpec1 = new PKCS8EncodedKeySpec(buffer);
//            BCECPrivateKey priKey = (BCECPrivateKey)keyFactory1.generatePrivate(keySpec1);
//
//            ECPrivateKeyParameters priKeyParameters = BCECUtil.convertPrivateKeyToParameters(priKey);

            String str = "1234567890123456";
//            byte[] sign = SM2Util.sign(priKeyParameters, str.getBytes());
//            System.out.println("SM2 sign without withId length:" + sign.length);
//            System.out.println("SM2 sign without withId result:\n" + ByteUtils.toHexString(sign));
//            System.out.println("SM2 sign without withId result:\n" + Hex.encodeHexStr(sign));
            byte[] sign = fileToBytes("C:\\Users\\86188\\Desktop\\test\\sm2sm3\\server_161.sig");
//            byte[] srcData = fileToBytes("C:\\Users\\86188\\Desktop\\test\\sm2sm3\\msg.txt");
            byte[] srcData = str.getBytes();
//            String sign1 = "3045022042c7866c6190a1147ec00002bff60bdfb0fce47edcc54df8ac8eb12fdfef861b022100eb963a09867b5b8a636bfaea9ec979a368935ed6f36ce88c1f491c55b05cb745";
            boolean flag = SM2Util.verify(pubKey,"1234567812345678".getBytes(),srcData, sign);
            System.out.println("SM2 sign without withId verify:"+flag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void test2(){
        try {

            String cer ="C:\\Users\\86188\\Desktop\\test\\pk.pem";
            //私钥路径
            String key = "C:\\Users\\86188\\Desktop\\test\\sm2.key";
            String privateStr = "f209503038024ef8cd3492efa663fce54b667cb6bc26c0e3eb109745eb743ca3";
            String publicKeyStr = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEOW50IcNxzi4RMNYjCBA4Wk1OwQp291kNYgxjxsFPf+NBeYeoRPa6HOselxzjtsyMDJQ3OIX/9PR48Hf5rnuhog==";
            String signPath = "C:\\Users\\86188\\Desktop\\test\\sm2.sig";
            String str = "11111111";


            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            BCECPublicKey pubKey = (BCECPublicKey)keyFactory.generatePublic(keySpec);

            BASE64Encoder base64Encoder=new BASE64Encoder();
            String publicKeyString = base64Encoder.encode(pubKey.getEncoded());
            System.out.println("-----------------publicKey---base64-----------------");
            System.out.println(publicKeyString);
            System.out.println("-----------------publicKey----base64----------------");

            //获取证书私钥
//            byte[] priKeyData = FileUtil.readFile(key);
//            ECPrivateKeyParameters priKeyParameters = BCECUtil.convertSEC1ToECPrivateKey(priKeyData);


//            BASE64Decoder base64Decoder1 = new BASE64Decoder();
//            byte[] buffer1 = base64Decoder1.decodeBuffer(privateStr);
//            KeyFactory keyFactory1 = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
//            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(buffer1);
//            BCECPrivateKey generatePrivate = (BCECPrivateKey)keyFactory1.generatePrivate(pkcs8EncodedKeySpec);
//
//            ECPrivateKeyParameters priKeyParameters = BCECUtil.convertPrivateKeyToParameters(generatePrivate);


//            byte[] priKeypem = FileUtil.readFile(key);
//            ByteArrayInputStream bIn = new ByteArrayInputStream(priKeypem);
//            PemReader pRdr = new PemReader(new InputStreamReader(bIn));
//            byte[] priKeyData;
//            try {
//                PemObject pemObject = pRdr.readPemObject();
//                priKeyData = pemObject.getContent();
//            } finally {
//                pRdr.close();
//            }
//            KeyFactory keyFactory1 = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
//            PKCS8EncodedKeySpec keySpec1 = new PKCS8EncodedKeySpec(priKeyData);
//            BCECPrivateKey priKey = (BCECPrivateKey)keyFactory1.generatePrivate(keySpec1);
//
//            ECPrivateKeyParameters priKeyParameters = BCECUtil.convertPrivateKeyToParameters(priKey);



            BigInteger privateKeyD = new BigInteger(privateStr, 16);
            X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
            ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());

            ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKeyD, domainParameters);



            byte[] sign = SM2Util.sign(privateKeyParameters, str.getBytes());
//            bytesToFile(sign,"C:\\Users\\86188\\Desktop\\test\\sm2.sig1");
            System.out.println("SM2 sign without withId length:" + sign.length);
            System.out.println("SM2 sign without withId result:\n" + ByteUtils.toHexString(sign));
            System.out.println("SM2 sign without withId result:\n" + Hex.encodeHexStr(sign));
            boolean flag = SM2Util.verify(pubKey, str.getBytes(), sign);
            System.out.println("SM2 sign without withId verify:"+flag);
            System.out.println("========================================================");
//            byte[] sign1 = fileToBytes("C:\\Users\\86188\\Desktop\\test\\sm2.sig1");
//            boolean flag1 = SM2Util.verify(pubKey, str.getBytes(), sign1);
//            System.out.println("SM2 sign without withId length1:" + sign1.length);
//            System.out.println("SM2 sign without withId result1:\n" + ByteUtils.toHexString(sign1));
//            System.out.println("SM2 sign without withId verify1:"+flag1);


            System.out.println("========================================================");
            byte[] sign3 = fileToBytes(signPath);
            boolean flag3 = SM2Util.verify(pubKey, str.getBytes(), sign3);
            System.out.println("SM2 sign without withId length3:" + sign3.length);
            System.out.println("SM2 sign without withId result3:\n" + ByteUtils.toHexString(sign3));
            System.out.println("SM2 sign without withId verify3:"+flag3);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public static void test3(){
        try {


            String publicKeyStr = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE3BFUBCZJwI36j3CPyJCJnCYqtFceBA5m3iGc1eP4+b3WRqNIVmi88HY1UIvAjwt2GMTr30BCjl7cTi0kWc+M9w=1";
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            BCECPublicKey pubKey = (BCECPublicKey)keyFactory.generatePublic(keySpec);

            BASE64Encoder base64Encoder=new BASE64Encoder();
            String publicKeyString = base64Encoder.encode(pubKey.getEncoded());
            System.out.println("-----------------publicKey---base64-----------------");
            System.out.println(publicKeyString);
            System.out.println("-----------------publicKey----base64----------------");

//            //获取证书公钥
//            X509Certificate cert = SM2CertUtil.getX509Certificate("C:\\Users\\86188\\Documents\\WeChat Files\\sange212782\\FileStorage\\File\\2020-12\\serverpulickey.pem");
//            BCECPublicKey pubKey = SM2CertUtil.getBCECPublicKey(cert);
//            BASE64Encoder base64Encoder=new BASE64Encoder();
//            String publicKeyString = base64Encoder.encode(pubKey.getEncoded());
//            System.out.println("-----------------publicKey---base64-----------------");
//            System.out.println(publicKeyString);
//            System.out.println("-----------------publicKey----base64----------------");



            String privateStr = "64fd4f1dd75af860470882e898fc95cb0de7f4d08d72765b683ba6a5acc0481e";




            BigInteger privateKeyD = new BigInteger(privateStr, 16);
            X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
            ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());

            ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKeyD, domainParameters);


            String str = "1234567890123456";
            byte[] sign = SM2Util.sign(privateKeyParameters, str.getBytes());
            bytesToFile(sign,"C:\\Users\\86188\\Desktop\\test\\16msg1.sig");
            System.out.println("SM2 sign without withId length:" + sign.length);
            System.out.println("SM2 sign without withId result:\n" + ByteUtils.toHexString(sign));
            System.out.println("SM2 sign without withId result:\n" + Hex.encodeHexStr(sign));
            boolean flag = SM2Util.verify(pubKey, str.getBytes(), sign);
            System.out.println("SM2 sign without withId verify:"+flag);


            System.out.println("========================================================");
            byte[] sign3 = fileToBytes("C:\\Users\\86188\\Documents\\WeChat Files\\sange212782\\FileStorage\\File\\2020-12\\16msg.sig");

            boolean flag3 = SM2Util.verify(pubKey, str.getBytes(), sign3);
            System.out.println("SM2 sign without withId length3:" + sign3.length);
            System.out.println("SM2 sign without withId result3:\n" + ByteUtils.toHexString(sign3));
            System.out.println("SM2 sign without withId verify3:"+flag3);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public static void test(){
        try {
            //证书路径
//            String cer = "C:\\Users\\86188\\Documents\\WeChat Files\\sange212782\\FileStorage\\File\\2020-12\\Cert1.pem";
            String cer ="C:\\Users\\86188\\Desktop\\my.pem";
                    //私钥路径
            String key = "192.168.0.7.key";

            //获取证书公钥
            X509Certificate cert = SM2CertUtil.getX509Certificate(cer);
            BCECPublicKey pubKey = SM2CertUtil.getBCECPublicKey(cert);
            BASE64Encoder base64Encoder=new BASE64Encoder();
            String publicKeyString = base64Encoder.encode(pubKey.getEncoded());
            System.out.println("-----------------publicKey---base64-----------------");
            System.out.println(publicKeyString);
            System.out.println("-----------------publicKey----base64----------------");

            //获取证书私钥
            byte[] priKeyData = FileUtil.readFile(key);
            ECPrivateKeyParameters priKeyParameters = BCECUtil.convertSEC1ToECPrivateKey(priKeyData);
            System.out.println("-----------------publicKey---base64-----------------");
            System.out.println(publicKeyString);
            System.out.println("-----------------publicKey----base64----------------");
            String str = "12345678";
            byte[] sign = SM2Util.sign(priKeyParameters, str.getBytes());
            System.out.println("SM2 sign without withId length:" + sign.length);
            System.out.println("SM2 sign without withId result:\n" + ByteUtils.toHexString(sign));
            System.out.println("SM2 sign without withId result:\n" + Hex.encodeHexStr(sign));

            String sign1 = "3045022042c7866c6190a1147ec00002bff60bdfb0fce47edcc54df8ac8eb12fdfef861b022100eb963a09867b5b8a636bfaea9ec979a368935ed6f36ce88c1f491c55b05cb745";
            boolean flag = SM2Util.verify(pubKey, str.getBytes(), Hex.decodeHex(sign1.toCharArray()));
            System.out.println("SM2 sign without withId verify:"+flag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    public static void test4() throws CryptoException, CertificateException, NoSuchProviderException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String cer = "C:\\Users\\86188\\Desktop\\test\\vrfykey.pem";
        String key = "C:\\Users\\86188\\Desktop\\test\\signkey.pem";
        String srcStr = "C:\\Users\\86188\\Desktop\\test\\readme.txt";
        String signStr = "C:\\Users\\86188\\Desktop\\test\\readme.sig";

        String priv = "9268620b2c1e672dc2b7d4eee244f318ae7ae64c7bdb6a1989cefe2c8e65c742";
        String pub = "041ebbf9d779ace9e810751c95fd9c0d82bebb1bbdf6f01dd92d53b778a4326200443cd1d1a2791cda341a8f91261268cb5621439d16f33f7da61cdcc37539531b";
        String publicKeyStr = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEHrv513ms6egQdRyV/ZwNgr67G7328B3ZLVO3eKQyYgBEPNHRonkc2jQaj5EmEmjLViFDnRbzP32mHNzDdTlTGw==";
        String privateKeyStr = "MIGHAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBG0wawIBAQQgkmhiCyweZy3Ct9Tu4kTzGK565kx722oZic7+LI5lx0KhRANCAAQeu/nXeazp6BB1HJX9nA2CvrsbvfbwHdktU7d4pDJiAEQ80dGieRzaNBqPkSYSaMtWIUOdFvM/faYc3MN1OVMb";

        String str = "11111111";

        BigInteger privateKeyD = new BigInteger(priv, 16);
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());
        ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKeyD, domainParameters);


        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        BCECPublicKey pubKey = (BCECPublicKey)keyFactory.generatePublic(keySpec);

        byte[] srcData = fileToBytes(srcStr);
        byte[] signData = fileToBytes(signStr);

        System.out.println("=========================签名字符直接验签===============================");
        byte[] sign = SM2Util.sign(privateKeyParameters, srcData);
        System.out.println("SM2 sign length:" + sign.length);
        System.out.println("SM2 sign result:" + ByteUtils.toHexString(sign));
        boolean flag = SM2Util.verify(pubKey, srcData, sign);
        System.out.println("SM2 sign verify:"+flag);

        System.out.println("=========================读取自己签名文件验签===============================");
//        bytesToFile(sign,"C:\\Users\\86188\\Desktop\\test\\readme.sig1");
//        byte[] sign1 = SM2Util.sign(privateKeyParameters, srcData);
        byte[] sign1 = fileToBytes("C:\\Users\\86188\\Desktop\\test\\readme.sig1");
        boolean flag1 = SM2Util.verify(pubKey, str.getBytes(), sign1);
        System.out.println("SM2 sign length1:" + sign1.length);
        System.out.println("SM2 sign result1:" + ByteUtils.toHexString(sign1));
        System.out.println("SM2 sign verify1:"+flag1);
        System.out.println("========================读取gmssl签名文件验签================================");

        boolean flag3 = SM2Util.verify(pubKey, srcData, signData);
        System.out.println("SM2 sign  length3:" + signData.length);
        System.out.println("SM2 sign  result3:" + ByteUtils.toHexString(signData));
        System.out.println("SM2 sign  verify3:"+flag3);

    }

    public static void test5() throws CryptoException, CertificateException, NoSuchProviderException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        String priv = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgJ86VTsVHVDotU9ifdhTMWctszNEx7anmBw3omkVHOFagCgYIKoEcz1UBgi2hRANCAATFxfE2BJ3D+SfrgPT5fIU8AXr1DNPeKOHHrf3YAOXIDR4YEyHpVPFou6R0dn+CPI9vBzrR3m+FI7VI3Qdm0n9v";
        String publicKeyStr = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAExcXxNgSdw/kn64D0+XyFPAF69QzT3ijhx6392ADlyA0eGBMh6VTxaLukdHZ/gjyPbwc60d5vhSO1SN0HZtJ/bw==";

        byte[] plainText = "11111111".getBytes(StandardCharsets.UTF_8);

        final BouncyCastleProvider bc = new BouncyCastleProvider();
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] privateDecode = base64Decoder.decodeBuffer(publicKeyStr);
        KeyFactory keyFactory1 = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
        PKCS8EncodedKeySpec keySpec1 = new PKCS8EncodedKeySpec(privateDecode);
        KeyFactory keyFact = KeyFactory.getInstance("EC", bc);
        PrivateKey priva = keyFact.generatePrivate(keySpec1);


        byte[] publicDecode = base64Decoder.decodeBuffer(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicDecode);
        BCECPublicKey pubKey = (BCECPublicKey)keyFactory.generatePublic(keySpec);

//        byte[] srcData = fileToBytes(srcStr);
//        byte[] signData = fileToBytes(signStr);
//
//        byte[] sign = SM2Util.sign(privateKeyParameters, srcData);
//        System.out.println("SM2 sign length:" + sign.length);
//        System.out.println("SM2 sign result:" + ByteUtils.toHexString(sign));
//        boolean flag = SM2Util.verify(pubKey, srcData, sign);
//        System.out.println("SM2 sign verify:"+flag);


        System.out.println("========================验签================================");
        String sign11 = "30450220033c8500b0f4ddf7dd8ce4640933f8b9683d26892c214aa21dfdf5fe5fed96d3022100c891e44f2866ace270c9ae3e1ad12a73d48c25aed8414abf98a4fe4c96ce166c";
        boolean flag4 = SM2Util.verify(pubKey,plainText, ByteUtils.fromHexString(sign11));
        System.out.println("SM2 sign  length4:" + ByteUtils.fromHexString(sign11).length);
        System.out.println("SM2 sign  resul4:" + sign11);
        System.out.println("SM2 sign  verify4:"+flag4);
    }

    public static void testgo() throws CryptoException, CertificateException, NoSuchProviderException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String cer = "C:\\Users\\86188\\Desktop\\test\\go\\publicKey.pem";
        String srcStr = "C:\\Users\\86188\\Desktop\\test\\go\\text.txt";
        String signStr = "C:\\Users\\86188\\Desktop\\test\\go\\text.sig";
        String publicKeyStr = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEJemPw2Mw3yVDpT0n0LrHtDWhr6anA0CpJgxBsqk8EdpExeSCeW17RtHSoNOIbbfWGZLvj/L3EYorNA0vA8BR0Q==";

        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] publicDecode = base64Decoder.decodeBuffer(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicDecode);
        BCECPublicKey pubKey = (BCECPublicKey)keyFactory.generatePublic(keySpec);

        byte[] srcData = fileToBytes(srcStr);
        byte[] signData = fileToBytes(signStr);

        boolean flag3 = SM2Util.verify(pubKey, srcData, signData);
        System.out.println("SM2 sign  length3:" + signData.length);
        System.out.println("SM2 sign  result3:" + ByteUtils.toHexString(signData));
        System.out.println("SM2 sign  verify3:"+flag3);

    }

    public static void makeCertificate(){
        try {
            //证书路径
            String cer = "C:\\Users\\86188\\Desktop\\test\\wws\\public1.cer";
            //私钥路径
            String key = "C:\\Users\\86188\\Desktop\\test\\wws\\private1.pem";
            String srcStr = "C:\\Users\\86188\\Desktop\\test\\wws\\text.txt";
            String signStr = "C:\\Users\\86188\\Desktop\\test\\wws\\text.sig1";
            //证书申请信息
            Map map = new HashMap();
            map.put("C","CN");
            map.put("O","yytek");
            map.put("OU","yytek");
            map.put("CN","yytek");
            map.put("ST","BJ");
            map.put("L","BJ");
            //生成证书
//            new SM2X509CertMakerUtil().makeCertificate(map,key,cer);

            //获取证书私钥
            byte[] priKeyData = FileUtil.readFile(key);
            ECPrivateKeyParameters priKeyParameters = BCECUtil.convertSEC1ToECPrivateKey(priKeyData);


            //获取证书公钥
            X509Certificate cert = SM2CertUtil.getX509Certificate(cer);
            BCECPublicKey pubKey = SM2CertUtil.getBCECPublicKey(cert);

            byte[] srcData = fileToBytes(srcStr);
            byte[] sign = SM2Util.sign(priKeyParameters, srcData);
            bytesToFile(sign,signStr);
            System.out.println("SM2 sign without withId result:\n" + ByteUtils.toHexString(sign));
            boolean flag = SM2Util.verify(pubKey, srcData, sign);
            System.out.println("SM2 sign without withId verify:"+flag);

            byte[] cipherText = SM2Util.encrypt(pubKey, srcData);
            System.out.println("SM2 encrypt result:\n" + ByteUtils.toHexString(cipherText));
            byte[] plain = SM2Util.decrypt(priKeyParameters, cipherText);
            System.out.println("SM2 decrypt result:\n" + ByteUtils.toHexString(plain));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void makePemCertificate(){
        try {
            //证书路径
            String cer = "C:\\Users\\86188\\Desktop\\test\\wws\\public.pem";
            //私钥路径
            String key = "C:\\Users\\86188\\Desktop\\test\\wws\\private.pem";
            String srcStr = "C:\\Users\\86188\\Desktop\\test\\wws\\text.txt";
            String signStr = "C:\\Users\\86188\\Desktop\\test\\wws\\text.sig";
            //证书申请信息
            Map map = new HashMap();
            map.put("C","CN");
            map.put("O","yytek");
            map.put("OU","yytek");
            map.put("CN","yytek");
            map.put("ST","BJ");
            map.put("L","BJ");
            //生成证书
            new SM2X509CertMakerUtil().makePemCertificate(map,key,cer);
            BASE64Encoder base64Encoder=new BASE64Encoder();

            //获取证书私钥
            byte[] priKeyData = FileUtil.readFile(key);
//            ECPrivateKeyParameters priKeyParameters = BCECUtil.convertSEC1ToECPrivateKey(priKeyData);
            byte[] pkcs8 = BCECUtil.convertECPrivateKeyPEMToPKCS8(new String(priKeyData));
            BCECPrivateKey privatek = BCECUtil.convertPKCS8ToECPrivateKey(pkcs8);
            ECPrivateKeyParameters priKeyParameters = BCECUtil.convertPrivateKeyToParameters(privatek);
            String privateKeyString = base64Encoder.encode(priKeyData);
            System.out.println("-----------------privateKey--------------------");
            System.out.println(privateKeyString);
            System.out.println("-----------------privateKey--------------------");

            String publicKeyStr = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE7GL/D7dM7jukxNf3y9SmXmskPNZG6k0wbd0mmVtYv/3aYSkUhO6n2u/qszETq4C4aK5W8uYCLqJRu0+YptOxDA==";

            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] publicDecode = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicDecode);
            BCECPublicKey pubKey = (BCECPublicKey)keyFactory.generatePublic(keySpec);

            byte[] srcData = fileToBytes(srcStr);
            byte[] sign = SM2Util.sign(priKeyParameters, srcData);
            bytesToFile(sign,signStr);
            System.out.println("SM2 sign without withId result:\n" + ByteUtils.toHexString(sign));
            boolean flag = SM2Util.verify(pubKey, srcData, sign);
            System.out.println("SM2 sign without withId verify:"+flag);

            byte[] cipherText = SM2Util.encrypt(pubKey, srcData);
            System.out.println("SM2 encrypt result:\n" + ByteUtils.toHexString(cipherText));
            byte[] plain = SM2Util.decrypt(priKeyParameters, cipherText);
            System.out.println("SM2 decrypt result:\n" + ByteUtils.toHexString(plain));

//            getX509CerCate(cer);

//            Map map = getSubjectDN(cer);
//            System.out.println(map.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testc(){
        try {
            //证书路径
            String cer ="C:\\Users\\86188\\Desktop\\test\\ly\\server_cert.pem";
            String srcPath ="C:\\Users\\86188\\Desktop\\test\\ly\\msg.txt";
            String signPath ="C:\\Users\\86188\\Desktop\\test\\ly\\msg.sig";

            //获取证书公钥
            X509Certificate cert = SM2CertUtil.getX509Certificate(cer);
            BCECPublicKey pubKey = SM2CertUtil.getBCECPublicKey(cert);
            BASE64Encoder base64Encoder=new BASE64Encoder();
            String publicKeyString = base64Encoder.encode(pubKey.getEncoded());
            System.out.println("-----------------publicKey---base64-----------------");
            System.out.println(publicKeyString);
            System.out.println("-----------------publicKey----base64----------------");
            byte[] srcData = fileToBytes(srcPath);
            byte[] signData = fileToBytes(signPath);

            boolean flag = SM2Util.verify(pubKey, srcData, signData);
            System.out.println("SM2 sign without withId verify:"+flag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) throws CertificateException, CryptoException, NoSuchProviderException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        testc();


    }

    public static Map getSubjectDN(String cerPath){
        Map map = new HashMap();
        try {
            X509Certificate x509Certificate = SM2CertUtil.getX509Certificate(cerPath);
            String subjectDN = x509Certificate.getSubjectDN().toString();
            String[] array = subjectDN.split(",");
            for(String str : array){
                String[] strs = str.split("=");
                map.put(strs[0],strs[1]);
            }
            SimpleDateFormat formator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long from = x509Certificate.getNotBefore().getTime();
            long to = x509Certificate.getNotAfter().getTime();
            map.put("from",formator.format(new Date(from)));
            map.put("to",formator.format(new Date(to)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static X509Certificate getX509CerCate(String cerPath) throws CertificateException, NoSuchProviderException, IOException {
        X509Certificate x509Certificate = SM2CertUtil.getX509Certificate(cerPath);
        System.out.println("读取Cer证书信息...");
        System.out.println("x509Certificate_SerialNumber_序列号___:"+x509Certificate.getSerialNumber());
        System.out.println("x509Certificate_getIssuerDN_发布方标识名___:"+x509Certificate.getIssuerDN());
        System.out.println("x509Certificate_getSubjectDN_主体标识___:"+x509Certificate.getSubjectDN());
        System.out.println("x509Certificate_getSigAlgOID_证书算法OID字符串___:"+x509Certificate.getSigAlgOID());
        System.out.println("x509Certificate_getNotBefore_证书有效期从___:"+x509Certificate.getNotBefore());
        System.out.println("x509Certificate_getNotBefore_证书有效期至___:"+x509Certificate.getNotAfter());
        System.out.println("x509Certificate_getSigAlgName_签名算法___:"+x509Certificate.getSigAlgName());
        System.out.println("x509Certificate_getVersion_版本号___:"+x509Certificate.getVersion());
        System.out.println("x509Certificate_getPublicKey_公钥___:"+x509Certificate.getPublicKey());
        Principal  principal= x509Certificate.getSubjectDN();
        return x509Certificate;
    }

}
