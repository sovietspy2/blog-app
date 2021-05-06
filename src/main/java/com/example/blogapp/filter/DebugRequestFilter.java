package com.example.blogapp.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
//@Order(2)
public class DebugRequestFilter implements Filter {

    //private final static Logger LOG = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        log.info("Initializing filter :{}", this);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String enabled = req.getHeader("debug-enabled");

        if ("true".equals(enabled)) {
            MDC.put("debugRequest", "debug");
        }

        chain.doFilter(request, response);

        MDC.put("debugRequest", "info");
    }

    @Override
    public void destroy() {
        log.info("Destructing filter :{}", this);
    }
}
