package org.dreamcat.vendor.zhugeio;

import java.util.Map;
import okhttp3.logging.HttpLoggingInterceptor;
import org.dreamcat.common.hc.okhttp.OkHttpUtil;
import org.dreamcat.common.hc.okhttp.RetrofitUtil;
import org.dreamcat.vendor.zhugeio.api.IZhugeIOComponent;
import org.dreamcat.vendor.zhugeio.upload.ZhugeIOUploadRequest;
import org.dreamcat.vendor.zhugeio.upload.query.UploadEventQuery;
import org.dreamcat.vendor.zhugeio.upload.result.UploadEventResult;

/**
 * Create by tuke on 2020/10/22
 */
public class ZhugeIOComponent implements IZhugeIOComponent {

    private final String appKey;
    private final ZhugeIOUploadRequest uploadRequest;

    public ZhugeIOComponent(String appKey, String secretKey) {
        this.appKey = appKey;
        this.uploadRequest = RetrofitUtil.getInstance4Json(
                ZhugeIOUploadRequest.BASE_URL, OkHttpUtil.newClient(
                        OkHttpUtil.newBasicInterceptor(appKey, secretKey),
                        OkHttpUtil.newHttpLoggingInterceptor(HttpLoggingInterceptor.Level.BODY)
                ))
                .create(ZhugeIOUploadRequest.class);
    }

    public UploadEventResult uploadEvent(UploadEventQuery query) {
        query.setAppKey(appKey);
        Map<String, Object> map = RetrofitUtil.unwrap(uploadRequest.uploadEvent(query));
        return UploadEventResult.from(map);
    }

    public UploadEventResult uploadEvent(Map<String, Object> body) {
        Map<String, Object> map = RetrofitUtil.unwrap(uploadRequest.uploadEvent(body));
        return UploadEventResult.from(map);
    }

}
