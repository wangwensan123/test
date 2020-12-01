package sm.cert;

import sm.BCECUtil;
import sm.SM2Util;
import sm.cert.exception.InvalidX500NameException;
import sm.cert.model.SubjectDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
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

            SM2X509CertMaker certMaker = buildCertMaker(mapDN);
            X509Certificate cert = certMaker.makeSSLEndEntityCert(csr);
            FileUtil.writeFile(cer, cert.getEncoded());
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

    public static SM2X509CertMaker buildCertMaker(Map map) throws InvalidAlgorithmParameterException,
        NoSuchAlgorithmException, NoSuchProviderException, InvalidX500NameException {
        X500Name issuerName = SubjectDN.buildRootCADN(map);
        KeyPair issKP = SM2Util.generateKeyPair();
        long certExpire = 20L * 365 * 24 * 60 * 60 * 1000; // 20年
        CertSNAllocator snAllocator = new RandomSNAllocator(); // 实际应用中可能需要使用数据库来保证证书序列号的唯一性。
        return new SM2X509CertMaker(issKP, certExpire, issuerName, snAllocator);
    }

    public static void main(String[] args) {
        try {
            //生成证书
            String cerName = "sm2";
            try {
                InetAddress ip4 = Inet4Address.getLocalHost();
                cerName  = ip4.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            //证书路径
            String cer = cerName + ".cer";
            //私钥路径
            String key = cerName + ".key";
            //证书申请信息
            Map map = new HashMap();
            map.put("C","CN");
            map.put("O","yytek");
            map.put("OU","yytek");
            map.put("CN","yytek");
            map.put("ST","BJ");
            map.put("L","BJ");
            //生成证书
            new SM2X509CertMakerUtil().makeCertificate(map,key,cer);

            //获取证书公钥
            X509Certificate cert = SM2CertUtil.getX509Certificate(cer);
            BCECPublicKey pubKey = SM2CertUtil.getBCECPublicKey(cert);
            BASE64Encoder base64Encoder=new BASE64Encoder();
            String publicKeyString = base64Encoder.encode(pubKey.getEncoded());
            System.out.println("-----------------publicKey--------------------");
            System.out.println(publicKeyString);
            System.out.println("-----------------publicKey--------------------");
            //获取证书私钥
            byte[] priKeyData = FileUtil.readFile(key);
            ECPrivateKeyParameters priKeyParameters = BCECUtil.convertSEC1ToECPrivateKey(priKeyData);
            String privateKeyString = base64Encoder.encode(priKeyData);
            System.out.println("-----------------privateKey--------------------");
            System.out.println(privateKeyString);
            System.out.println("-----------------privateKey--------------------");

            byte[] sign = SM2Util.sign(priKeyParameters, GMBase.WITH_ID, GMBase.SRC_DATA);
            System.out.println("SM2 sign with withId result:\n" + ByteUtils.toHexString(sign));
            boolean flag = SM2Util.verify(pubKey, GMBase.WITH_ID, GMBase.SRC_DATA, sign);
            System.out.println("SM2 sign with withId verify:"+flag);

            sign = SM2Util.sign(priKeyParameters, GMBase.SRC_DATA);
            System.out.println("SM2 sign without withId result:\n" + ByteUtils.toHexString(sign));
            flag = SM2Util.verify(pubKey, GMBase.SRC_DATA, sign);
            System.out.println("SM2 sign without withId verify:"+flag);

            byte[] cipherText = SM2Util.encrypt(pubKey, GMBase.SRC_DATA);
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
