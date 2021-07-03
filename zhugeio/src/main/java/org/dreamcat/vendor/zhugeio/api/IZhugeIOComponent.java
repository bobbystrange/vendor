package org.dreamcat.vendor.zhugeio.api;

import java.util.Map;
import org.dreamcat.vendor.zhugeio.upload.result.UploadEventResult;

/**
 * Create by tuke on 2020/10/22
 */
public interface IZhugeIOComponent {

    UploadEventResult uploadEvent(Map<String, Object> body);
}
