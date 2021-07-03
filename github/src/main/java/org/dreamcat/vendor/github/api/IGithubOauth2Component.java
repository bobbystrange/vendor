package org.dreamcat.vendor.github.api;

import org.dreamcat.vendor.github.query.AuthorizeQuery;
import org.dreamcat.vendor.github.query.Oauth2AccessTokenQuery;
import org.dreamcat.vendor.github.view.Oauth2AccessTokenView;

/**
 * Create by tuke on 2020/6/5
 */
public interface IGithubOauth2Component {

    String concatAuthorizeUrl(AuthorizeQuery query);

    Oauth2AccessTokenView postOauth2AccessToken(Oauth2AccessTokenQuery query, String code);


}
