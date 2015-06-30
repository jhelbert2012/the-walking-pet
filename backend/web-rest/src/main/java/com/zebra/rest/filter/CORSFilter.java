package com.zebra.rest.filter;

import com.zebra.context.ContextHolder;
import org.codehaus.plexus.util.StringUtils;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.engine.header.Header;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.routing.Filter;
import org.restlet.util.Series;

public class CORSFilter extends Filter {

    private static final String X_FORWARDED_PROTO_HEADER = "X-Forwarded-Proto";

    @Override
    protected int beforeHandle(Request request, Response response) {

        Series headers = (Series) request.getAttributes().get("org.restlet.http.headers");
        if (!StringUtils.isEmpty(headers.getFirstValue(X_FORWARDED_PROTO_HEADER))) {
            String forwardedProto = headers.getFirstValue(X_FORWARDED_PROTO_HEADER);
            ContextHolder.setProtocol(forwardedProto.toLowerCase());
        }

        if (Method.OPTIONS.equals(request.getMethod())) {
			// Form requestHeaders = (Form)
            // request.getAttributes().get("org.restlet.http.headers");
            // String origin = requestHeaders.getFirstValue("Origin", true);

            // if(MyConfig.getAllowedOrigins().contains(origin)) {
            @SuppressWarnings("unchecked")
            Series<Header> responseHeaders = (Series<Header>) response.getAttributes().get("org.restlet.http.headers");
            if (responseHeaders == null) {
                responseHeaders = new Series<Header>(Header.class);
                response.getAttributes().put("org.restlet.http.headers", responseHeaders);
            }
            responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));// origin);
            responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET,POST,DELETE,OPTIONS"));
            responseHeaders.add(new Header("Access-Control-Allow-Headers", "Content-Type"));
            responseHeaders.add(new Header("Access-Control-Allow-Credentials", "true"));
            responseHeaders.add(new Header("Access-Control-Max-Age", "60"));
            response.setEntity(new EmptyRepresentation());
            return SKIP;
        }
        // }

        return super.beforeHandle(request, response);
    }

    @Override
    protected void afterHandle(Request request, Response response) {
        if (!Method.OPTIONS.equals(request.getMethod())) {
			// Form requestHeaders = (Form)
            // request.getAttributes().get("org.restlet.http.headers");
            // String origin = requestHeaders.getFirstValue("Origin", true);

            // if(MyConfig.getAllowedOrigins().contains(origin)) {
            @SuppressWarnings("unchecked")
            Series<Header> responseHeaders = (Series<Header>) response.getAttributes().get("org.restlet.http.headers");
            if (responseHeaders == null) {
                responseHeaders = new Series<Header>(Header.class);
                response.getAttributes().put("org.restlet.http.headers", responseHeaders);
            }
            responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));// origin);
            responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET,POST,DELETE,OPTIONS"));
            responseHeaders.add(new Header("Access-Control-Allow-Headers", "Content-Type"));
            responseHeaders.add(new Header("Access-Control-Allow-Credentials", "true"));
            responseHeaders.add(new Header("Access-Control-Max-Age", "60"));
            // }
        }
        super.afterHandle(request, response);
    }
}
