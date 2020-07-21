package org.dreamcat.vendor.oauth2.github.api;

import org.dreamcat.vendor.oauth2.github.query.AuthorizeQuery;
import org.dreamcat.vendor.oauth2.github.query.Oauth2AccessTokenQuery;
import org.dreamcat.vendor.oauth2.github.view.Oauth2AccessTokenView;

/**
 * Create by tuke on 2020/6/5
 */
public interface IGithubComponent {

    String concatAuthorizeUrl(AuthorizeQuery query);

    Oauth2AccessTokenView postOauth2AccessToken(Oauth2AccessTokenQuery query, String code);


}
