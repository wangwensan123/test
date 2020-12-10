package sm.gb;


import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.test.TestRandomBigInteger;
import sm.BCECUtil;
import sm.cert.Hex;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;

public class SM2SignerTest1{
    private static final ECDomainParameters PARAMS_FP_DRAFT = createParamsFpDraft();
    private static final ECDomainParameters PARAMS_F2M = createParamsF2m();

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public String getName()
    {
        return "SM2Signer";
    }

    public static void doSignerTestFpDraftSM3()
            throws Exception
    {
        doSignerTest(
                PARAMS_FP_DRAFT,
                new SM3Digest(),
                "ALICE123@YAHOO.COM",//用户身份
                "message digest",//消息M
                "128B2FA8BD433C6C068C8D803DFF79792A519A55171B1B650C23661D15897263",//私钥
                "6CB28D99385C175C94F94E934817663FC176D925DD72B727260DBAAE1FB2F96F",//随机数K
                "40F1EC59F793D9F49E09DCEF49130D4194F79FB1EED2CAA55BACDB49C4E755D1",//消息M的r
                "6FC6DAC32C5D5CF10C77DFB20F7C2EB667A457872FB09EC56327A67EC7DEEBE7"//消息M的s
        );
    }

    public static void doSignerTestFpDraftSha256()
            throws Exception
    {
        doSignerTest(
                PARAMS_FP_DRAFT,
                new SHA256Digest(),
                "ALICE123@YAHOO.COM",
                "message digest",
                "128B2FA8BD433C6C068C8D803DFF79792A519A55171B1B650C23661D15897263",
                "6CB28D99385C175C94F94E934817663FC176D925DD72B727260DBAAE1FB2F96F",
                "7D62A5EDBDDC8AF4D69C9E37A60D31F5CEFE8727709117E0869648D0A9AE4F57",
                "1E5E89718B716AAFC6253443168E4F7CF7E1B7B3934307686CE5947C1BD55EDA"
        );
    }

    public static void doSignerTestFpStandardSM31() throws Exception {
        doSignerTest(
                "sm2p256v1",
                new SM3Digest(),
                "ALICE123@YAHOO.COM",
                "message digest",
                "110E7973206F68C19EE5F7328C036F26911C8C73B4E4F36AE3291097F8984FFC",
                "3174C6FFC3C279D2422F3FC0A9F3E574674A4490FE45A5325CAF7D3EC4C8F96C",
                "05890B9077B92E47B17A1FF42A814280E556AFD92B4A98B9670BF8B1A274C2FA",
                "E3ABBB8DB2B6ECD9B24ECCEA7F679FB9A4B1DB52F4AA985E443AD73237FA1993"
        );
    }

    public static void doSignerTestFpStandardSM3() throws Exception {
        doSignerTest(
                "sm2p256v1",
                new SM3Digest(),
                "sm2test@example.com",
                "hi chappy",
                "110E7973206F68C19EE5F7328C036F26911C8C73B4E4F36AE3291097F8984FFC",
                "3174C6FFC3C279D2422F3FC0A9F3E574674A4490FE45A5325CAF7D3EC4C8F96C",
                "05890B9077B92E47B17A1FF42A814280E556AFD92B4A98B9670BF8B1A274C2FA",
                "E3ABBB8DB2B6ECD9B24ECCEA7F679FB9A4B1DB52F4AA985E443AD73237FA1993"
        );
    }

    public static void doSignerTestFpStandardSha256()
            throws Exception
    {
        doSignerTest(
                "sm2p256v1",
                new SHA256Digest(),
                "sm2test@example.com",
                "hi chappy",
                "110E7973206F68C19EE5F7328C036F26911C8C73B4E4F36AE3291097F8984FFC",
                "3174C6FFC3C279D2422F3FC0A9F3E574674A4490FE45A5325CAF7D3EC4C8F96C",
                "94DA20EA69E4FC70692158BF3D30F87682A4B2F84DF4A4829A1EFC5D9C979D3F",
                "EE15AF8D455B728AB80E592FCB654BF5B05620B2F4D25749D263D5C01FAD365F"
        );
    }

    public static void doSignerTestFpP256SM3()
            throws Exception
    {
        doSignerTest(
                "P-256",
                new SM3Digest(),
                "sm2_p256_test@example.com",
                "no backdoors here",
                "110E7973206F68C19EE5F7328C036F26911C8C73B4E4F36AE3291097F8984FFC",
                "3174C6FFC3C279D2422F3FC0A9F3E574674A4490FE45A5325CAF7D3EC4C8F96C",
                "96AA39A0C4A5C454653F394E86386F2E38BE14C57D0E555F3A27A5CEF30E51BD",
                "62372BE4AC97DBE725AC0B279BB8FD15883858D814FD792DDB0A401DCC988E70"
        );
    }

    public static void doSignerTestFpP256Sha256()
            throws Exception
    {
        doSignerTest(
                "P-256",
                new SHA256Digest(),
                "sm2_p256_test@example.com",
                "no backdoors here",
                "110E7973206F68C19EE5F7328C036F26911C8C73B4E4F36AE3291097F8984FFC",
                "3174C6FFC3C279D2422F3FC0A9F3E574674A4490FE45A5325CAF7D3EC4C8F96C",
                "503D234A22123D7029271EB9E0D763619A69868DE8296C13EDD4CA32D280CFDE",
                "0BDE97699B77268584DDD238DA120095F01130AD2DB37184270F37C02FB2E86B"
        );
    }

    public static void doSignerTestF2m()
            throws Exception
    {
        doSignerTest(
                PARAMS_F2M,
                new SM3Digest(),
                "ALICE123@YAHOO.COM",
                "message digest",
                "771EF3DBFF5F1CDC32B9C572930476191998B2BF7CB981D7F5B39202645F0931",
                "36CD79FC8E24B7357A8A7B4A46D454C397703D6498158C605399B341ADA186D6",
                "6D3FBA26EAB2A1054F5D198332E335817C8AC453ED26D3391CD4439D825BF25B",
                "3124C5688D95F0A10252A9BED033BEC84439DA384621B6D6FAD77F94B74A9556"
        );
    }

    public static void doSignerTest(String curveName, Digest d, String ident, String msg, String x, String nonce, String r, String s)
            throws Exception
    {
        X9ECParameters x9 = ECNamedCurveTable.getByName(curveName);
        ECDomainParameters domainParams = new ECDomainParameters(x9.getCurve(), x9.getG(), x9.getN(), x9.getH(), x9.getSeed());

        doSignerTest(domainParams, d, ident, msg, x, nonce, r, s);
    }


    public static void doSignerTest(ECDomainParameters domainParams, Digest d, String ident, String msg, String x, String nonce, String r, String s)
            throws Exception
    {
        byte[] idBytes = Strings.toByteArray(ident);
        byte[] msgBytes = Strings.toByteArray(msg);
        AsymmetricCipherKeyPair kp = generateKeyPair(domainParams, x);

        SM2Signer signer = new SM2Signer(d);

        signer.init(true, new ParametersWithID(
                new ParametersWithRandom(kp.getPrivate(), new TestRandomBigInteger(nonce, 16)),
                idBytes));

        signer.update(msgBytes, 0, msgBytes.length);

        byte[] sig = signer.generateSignature();

        BigInteger[] rs = decode(sig);

        System.out.println("r wrong:"+rs[0].equals(new BigInteger(r, 16)));
        System.out.println("s wrong:"+ rs[1].equals(new BigInteger(s, 16)));

        signer.init(false, new ParametersWithID(kp.getPublic(), idBytes));

        signer.update(msgBytes, 0, msgBytes.length);

        System.out.println("verification failed:"+ signer.verifySignature(sig));
    }



    public static void doVerifyBoundsCheck()
            throws IOException
    {
        ECDomainParameters domainParams = PARAMS_F2M;

        AsymmetricCipherKeyPair kp = generateKeyPair(domainParams, "771EF3DBFF5F1CDC32B9C572930476191998B2BF7CB981D7F5B39202645F0931");

        SM2Signer signer = new SM2Signer();

        signer.init(false, kp.getPublic());

        signer.update(new byte[20], 0, 20);
        System.out.println(!signer.verifySignature(encode(ECConstants.ZERO, ECConstants.EIGHT)));

        signer.update(new byte[20], 0, 20);
        System.out.println(!signer.verifySignature(encode(ECConstants.EIGHT, ECConstants.ZERO)));

        signer.update(new byte[20], 0, 20);
        System.out.println(!signer.verifySignature(encode(domainParams.getN(), ECConstants.EIGHT)));

        signer.update(new byte[20], 0, 20);
        System.out.println(!signer.verifySignature(encode(ECConstants.EIGHT, domainParams.getN())));
    }


    public static ECDomainParameters createParamsFpDraft()
    {
        BigInteger SM2_ECC_P = new BigInteger("8542D69E4C044F18E8B92435BF6FF7DE457283915C45517D722EDB8B08F1DFC3", 16);
        BigInteger SM2_ECC_A = new BigInteger("787968B4FA32C3FD2417842E73BBFEFF2F3C848B6831D7E0EC65228B3937E498", 16);
        BigInteger SM2_ECC_B = new BigInteger("63E4C6D3B23B0C849CF84241484BFE48F61D59A5B16BA06E6E12D1DA27C5249A", 16);
        BigInteger SM2_ECC_N = new BigInteger("8542D69E4C044F18E8B92435BF6FF7DD297720630485628D5AE74EE7C32E79B7", 16);
        BigInteger SM2_ECC_H = ECConstants.ONE;
        BigInteger SM2_ECC_GX = new BigInteger("421DEBD61B62EAB6746434EBC3CC315E32220B3BADD50BDC4C4E6C147FEDD43D", 16);
        BigInteger SM2_ECC_GY = new BigInteger("0680512BCBB42C07D47349D2153B70C4E5D7FDFCBFA36EA1A85841B9E46E09A2", 16);

        ECCurve curve = new ECCurve.Fp(SM2_ECC_P, SM2_ECC_A, SM2_ECC_B, SM2_ECC_N, SM2_ECC_H);
        ECPoint g = curve.createPoint(SM2_ECC_GX, SM2_ECC_GY);
        return new ECDomainParameters(curve, g, SM2_ECC_N, SM2_ECC_H);
    }

    public static ECDomainParameters createParamsF2m()
    {
        BigInteger SM2_ECC_A = new BigInteger("00", 16);
        BigInteger SM2_ECC_B = new BigInteger("E78BCD09746C202378A7E72B12BCE00266B9627ECB0B5A25367AD1AD4CC6242B", 16);
        BigInteger SM2_ECC_N = new BigInteger("7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFBC972CF7E6B6F900945B3C6A0CF6161D", 16);
        BigInteger SM2_ECC_H = BigInteger.valueOf(4);
        BigInteger SM2_ECC_GX = new BigInteger("00CDB9CA7F1E6B0441F658343F4B10297C0EF9B6491082400A62E7A7485735FADD", 16);
        BigInteger SM2_ECC_GY = new BigInteger("013DE74DA65951C4D76DC89220D5F7777A611B1C38BAE260B175951DC8060C2B3E", 16);

        ECCurve curve = new ECCurve.F2m(257, 12, SM2_ECC_A, SM2_ECC_B, SM2_ECC_N, SM2_ECC_H);
        ECPoint g = curve.createPoint(SM2_ECC_GX, SM2_ECC_GY);
        return new ECDomainParameters(curve, g, SM2_ECC_N, SM2_ECC_H);
    }

    public static BigInteger[] decode(byte[] sig)
    {
        ASN1Sequence s = ASN1Sequence.getInstance(sig);

        return new BigInteger[] {
                decodeValue(s.getObjectAt(0)),
                decodeValue(s.getObjectAt(1)) };
    }

    public static BigInteger decodeValue(ASN1Encodable e)
    {
        return ASN1Integer.getInstance(e).getValue();
    }

    public static byte[] encode(BigInteger r, BigInteger s)
            throws IOException
    {
        return new DERSequence(new ASN1Encodable[] { new ASN1Integer(r), new ASN1Integer(s)}).getEncoded();
    }

    public static AsymmetricCipherKeyPair generateKeyPair(ECDomainParameters domainParams, String x)
    {
        ECKeyPairGenerator kpg = new ECKeyPairGenerator();
        kpg.init(new ECKeyGenerationParameters(domainParams, new TestRandomBigInteger(x, 16)));
        return kpg.generateKeyPair();
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

    public static void doSignerTestsign(byte[] msgBytes, String x, byte[] sigData) throws Exception {
        X9ECParameters x9 = ECNamedCurveTable.getByName("sm2p256v1");
        ECDomainParameters domainParams = new ECDomainParameters(x9.getCurve(), x9.getG(), x9.getN(), x9.getH(), x9.getSeed());

        AsymmetricCipherKeyPair kp = generateKeyPair(domainParams, x);

        System.out.println(kp.getPublic());

        SM2Signer signer = new SM2Signer();
        ParametersWithRandom pwr = new ParametersWithRandom(kp.getPrivate(), new SecureRandom());
        signer.init(true, pwr);

        signer.update(msgBytes, 0, msgBytes.length);

        byte[] sig = signer.generateSignature();
        String sigStr = Hex.encodeHexStr(sig);
        System.out.println("sigStr:"+sigStr);
//        bytesToFile(sig,"C:\\Users\\86188\\Desktop\\test\\readme.sig99");

        signer.init(false, kp.getPublic());
        signer.update(msgBytes, 0, msgBytes.length);

        System.out.println("verification current:"+ signer.verifySignature(sig));

        signer.init(false, kp.getPublic());
        signer.update(msgBytes, 0, msgBytes.length);

        System.out.println("verification input sign:"+ signer.verifySignature(sigData));
    }

    public static void doSignerTestverify(byte[] msgBytes, String publicKeyStr, byte[] sigData) throws Exception {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        BCECPublicKey pubKey = (BCECPublicKey)keyFactory.generatePublic(keySpec);
        ECPublicKeyParameters pubKeyParameters = BCECUtil.convertPublicKeyToParameters(pubKey);
        SM2Signer signer = new SM2Signer();


        signer.init(false, pubKeyParameters);
        signer.update(msgBytes, 0, msgBytes.length);

        System.out.println("verification input sign:"+ signer.verifySignature(sigData));
    }


    public static void doSignerTestFpStandardSM3My() throws Exception {
//        String signStr = "3046022100983fbcdf7e533df5c6ea903377ce838cf07fd378f676b08ddb33f53ba914c6e9022100c1949dd4aaa41e2f6813ed5c2150c193babf3314450ed0948a03ba4ea10ba309";
//        String privateKey =  "110E7973206F68C19EE5F7328C036F26911C8C73B4E4F36AE3291097F8984FFC";
//        byte[] srcData = Strings.toByteArray("12345678");
        String privateKey = "9268620b2c1e672dc2b7d4eee244f318ae7ae64c7bdb6a1989cefe2c8e65c742";
        String publicKeyStr = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEHrv513ms6egQdRyV/ZwNgr67G7328B3ZLVO3eKQyYgBEPNHRonkc2jQaj5EmEmjLViFDnRbzP32mHNzDdTlTGw==";
        String srcPath = "C:\\Users\\86188\\Desktop\\test\\readme.txt";
        String signPath = "C:\\Users\\86188\\Desktop\\test\\readme.sig99";
        byte[] srcData = fileToBytes(srcPath);
        byte[] signData = fileToBytes(signPath);
//        String signStr = "30440220565dedac4b0370658eec2f47ddfb667421f585129be2911faeef63b9866c98710220125cbc9c4b36a3c99c2838d0070bbffec39752aa2ee022b3db355d8611d62138";
//        byte[] signData = Hex.decodeHex(signStr.toCharArray());
        doSignerTestsign(srcData,privateKey,signData);
        doSignerTestverify(srcData,publicKeyStr,signData);

    }

    public static void doSignerTestFpStandardSM3wws() throws Exception {

        String privateKey = "9268620b2c1e672dc2b7d4eee244f318ae7ae64c7bdb6a1989cefe2c8e65c742";
        String publicKeyStr = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE7GL/D7dM7jukxNf3y9SmXmskPNZG6k0wbd0mmVtYv/3aYSkUhO6n2u/qszETq4C4aK5W8uYCLqJRu0+YptOxDA==";
        String srcPath = "C:\\Users\\86188\\Desktop\\test\\wws\\text.txt";
        String signPath = "C:\\Users\\86188\\Desktop\\test\\wws\\text.sig";
        byte[] srcData = fileToBytes(srcPath);
        byte[] signData = fileToBytes(signPath);
//        doSignerTestsign(srcData,privateKey,signData);
        doSignerTestverify(srcData,publicKeyStr,signData);

    }

    public static void main(String[] args) throws Exception {
//        doSignerTestFpDraftSM3();
//        doSignerTestFpDraftSha256();
//        doSignerTestFpStandardSM3();
//        System.out.println("===============================");
//        doSignerTestFpStandardSM31();
//        System.out.println("===============================");
        doSignerTestFpStandardSM3wws();
//        doSignerTestFpStandardSha256();
//        doSignerTestFpP256SM3();
//        doSignerTestFpP256Sha256();
//        doSignerTestF2m();
//        doVerifyBoundsCheck();
    }
}

