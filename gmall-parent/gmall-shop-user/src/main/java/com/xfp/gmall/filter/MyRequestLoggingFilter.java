package com.xfp.gmall.filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class MyRequestLoggingFilter extends CommonsRequestLoggingFilter {


    @Override
    protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
        StringBuilder msg = new StringBuilder();
        msg.append(prefix);
        msg.append("method=").append(request.getMethod().toUpperCase()).append(";");
        msg.append("uri=").append(request.getRequestURI());

        if (isIncludeQueryString()) {
            String queryString = request.getQueryString();
            if (queryString != null) {
                msg.append('?').append(queryString);
            }
        }

        if (isIncludeClientInfo()) {
            String client = request.getRemoteAddr();
            if (StringUtils.hasLength(client)) {
                msg.append(";client=").append(client);
            }
            HttpSession session = request.getSession(false);
            if (session != null) {
                msg.append(";session=").append(session.getId());
            }
            String user = request.getRemoteUser();
            if (user != null) {
                msg.append(";user=").append(user);
            }
        }

        if (isIncludeHeaders()) {
            msg.append(";headers=").append(new ServletServerHttpRequest(request).getHeaders());
        }

        if (isIncludePayload()) {
            String payload = getMessagePayload(request);
            if (payload != null) {
                msg.append(";payload=").append(payload);
            }
        }

        msg.append(suffix);
        return msg.toString();
    }


}
