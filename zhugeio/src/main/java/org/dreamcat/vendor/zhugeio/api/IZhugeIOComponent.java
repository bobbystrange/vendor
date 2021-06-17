package org.dreamcat.vendor.zhugeio.api;

import org.dreamcat.vendor.zhugeio.upload.result.UploadEventResult;

import java.util.Map;

/**
 * Create by tuke on 2020/10/22
 */
public interface IZhugeIOComponent {

    UploadEventResult uploadEvent(Map<String, Object> body);
}
