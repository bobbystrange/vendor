package org.dreamcat.vendor.cdn.util;

import com.tukeof.common.util.SignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by tuke on 2019-03-31
 */
@Slf4j
public class QcloudCdnSignUtilTest {

    @Test
    public void sign() {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("Action", "DescribeCdnHosts");
        queryMap.put("SecretId", "AKIDT8G5AsY1D3MChWooNq1rFSw1fyBVCX9D");
        queryMap.put("Timestamp", 1463122059);
        queryMap.put("Nonce", 13029);
        queryMap.put("limit", 10);
        queryMap.put("offset", 0);

        String paramString = SignatureUtil.joinSorted(
                queryMap,
                it -> it.replace('_', '.'),
                Object::toString);
        log.info("paramString:\t{}", paramString);
        assert paramString.equals("Action=DescribeCdnHosts&Nonce=13029&SecretId=AKIDT8G5AsY1D3MChWooNq1rFSw1fyBVCX9D&Timestamp=1463122059&limit=10&offset=0");

        String signatureString = "GET" + "cdn.api.qcloud.com/v2/index.php" + "?" + paramString;
        log.info("signatureString:\t{}", signatureString);
        assert signatureString.equals("GETcdn.api.qcloud.com/v2/index.php?Action=DescribeCdnHosts&Nonce=13029&SecretId=AKIDT8G5AsY1D3MChWooNq1rFSw1fyBVCX9D&Timestamp=1463122059&limit=10&offset=0");

        String sign = SignatureUtil.hmacsha1Base64(signatureString, "pxPgRWDbCy86ZYyqBTDk7WmeRZSmPco0");
        log.info("sign:\t{}", sign);
        assert sign.equals("bWMMAR1eFGjZ5KWbfxTlBiLiNLc=");
    }
}
