package sm.cert.model;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;

import java.util.Map;

public class SubjectDN {

    public static X500Name buildSubjectDN(Map<String, String> map) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.C, map.get("C"));
        builder.addRDN(BCStyle.O, map.get("O"));
        builder.addRDN(BCStyle.OU, map.get("OU"));
        builder.addRDN(BCStyle.CN, map.get("CN"));
        builder.addRDN(BCStyle.ST, map.get("ST"));
        builder.addRDN(BCStyle.L, map.get("L"));
        return builder.build();
    }

    public static X500Name buildRootCADN(Map<String, String> map) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.C, map.get("C"));
        builder.addRDN(BCStyle.O, map.get("O"));
        builder.addRDN(BCStyle.OU, map.get("OU"));
        builder.addRDN(BCStyle.CN, map.get("CN"));
        return builder.build();
    }
}
