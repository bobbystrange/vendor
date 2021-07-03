package org.dreamcat.vendor.weixin.api;

import org.dreamcat.vendor.weixin.mini.query.ModifyDomainQuery;
import org.dreamcat.vendor.weixin.mini.view.ModifyDomainView;

/**
 * Create by tuke on 2019-05-28
 */
public interface IWXMiniComponent {

    // 1、设置小程序服务器域名
    // 授权给第三方的小程序，其服务器域名只可以为第三方的服务器，
    // 当小程序通过第三方发布代码上线后，小程序原先自己配置的服务器域名将被删除，只保留第三方平台的域名，
    // 所以第三方平台在代替小程序发布代码之前，需要调用接口为小程序添加第三方自身的域名。
    // POST https://api.weixin.qq.com/wxa/modify_domain?access_token=TOKEN
    ModifyDomainView modifyDomain(ModifyDomainQuery query, String accessToken);


}
