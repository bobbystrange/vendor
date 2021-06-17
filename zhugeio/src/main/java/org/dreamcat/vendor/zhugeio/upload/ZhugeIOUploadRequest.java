package org.dreamcat.vendor.zhugeio.upload;

import org.dreamcat.vendor.zhugeio.upload.query.UploadEventQuery;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.Map;

/**
 * Create by tuke on 2020/10/22
 */
public interface ZhugeIOUploadRequest {
    String BASE_URL = "https://u.zhugeapi.net";

    @POST("/open/v2/event_statis_srv/upload_event")
    Call<Map<String, Object>> uploadEvent(@Body UploadEventQuery body);

    @POST("/open/v2/event_statis_srv/upload_event")
    Call<Map<String, Object>> uploadEvent(@Body Map<String, Object> body);
}
