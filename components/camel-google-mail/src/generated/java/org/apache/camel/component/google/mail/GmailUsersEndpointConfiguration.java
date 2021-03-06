
/*
 * Camel EndpointConfiguration generated by camel-api-component-maven-plugin
 */
package org.apache.camel.component.google.mail;

import org.apache.camel.spi.Configurer;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;

/**
 * Camel EndpointConfiguration for com.google.api.services.gmail.Gmail$Users
 */
@UriParams(apiName = "users")
@Configurer
public final class GmailUsersEndpointConfiguration extends GoogleMailConfiguration {
    @UriParam
    private com.google.api.services.gmail.model.WatchRequest content;
    @UriParam
    private String userId;

    public com.google.api.services.gmail.model.WatchRequest getContent() {
        return content;
    }

    public void setContent(com.google.api.services.gmail.model.WatchRequest content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
