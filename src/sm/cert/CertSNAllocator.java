package sm.cert;

import java.math.BigInteger;

public interface CertSNAllocator {
    BigInteger nextSerialNumber() throws Exception;
}
