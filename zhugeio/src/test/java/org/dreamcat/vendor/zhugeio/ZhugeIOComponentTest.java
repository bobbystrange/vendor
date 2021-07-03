package org.dreamcat.vendor.zhugeio;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Properties;
import org.dreamcat.common.hc.gson.GsonUtil;
import org.dreamcat.vendor.zhugeio.upload.query.UploadEventQuery;
import org.dreamcat.vendor.zhugeio.upload.result.UploadEventResult;
import org.junit.Before;
import org.junit.Test;

/**
 * Create by tuke on 2020/10/22
 */
public class ZhugeIOComponentTest {

    private ZhugeIOComponent zhugeIOComponent;

    @Before
    public void init() throws IOException {
        Properties properties = new Properties();
        URL url = getClass().getClassLoader().getResource(".");
        assert url != null;
        File projectDir = new File(url.getFile())
                .getParentFile().getParentFile().getParentFile().getParentFile();
        File localFile = new File(projectDir, "local.properties");
        try (Reader reader = new FileReader(localFile)) {
            properties.load(reader);
        }
        String appKey = properties.getProperty("zhugeio_app_key");
        String secretKey = properties.getProperty("zhugeio_secret_key");
        zhugeIOComponent = new ZhugeIOComponent(appKey, secretKey);
    }

    @Test
    public void testEvent() {
        UploadEventQuery query = newUploadEventQuery();
        UploadEventResult result = zhugeIOComponent.uploadEvent(query);
        System.out.println(GsonUtil.toJson(result));
    }

    @Test
    public void testJson() {
        UploadEventQuery query = newUploadEventQuery();
        System.out.println(GsonUtil.toJson(query));
    }

    private UploadEventQuery newUploadEventQuery() {
        UploadEventQuery query = new UploadEventQuery();
        query.setDataType(UploadEventQuery.DataType.EVT.getValue());
        query.setDebug(1);
        UploadEventQuery.PushRequst pushRequst = new UploadEventQuery.PushRequst();
        pushRequst.setCt(System.currentTimeMillis());
        pushRequst.setEid("testEvent");
        pushRequst.setCuid("bobby");
        query.setPushRequst(pushRequst);
        return query;
    }
}
