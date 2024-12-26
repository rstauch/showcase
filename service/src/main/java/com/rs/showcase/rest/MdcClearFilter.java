package com.rs.showcase.rest;

import jakarta.servlet.*;
import java.io.IOException;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.MDC;

public class MdcClearFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      chain.doFilter(request, response);
    } finally {
      // clean up MDC after each request
      MDC.clear();
      ThreadContext.clearAll();
    }
  }
}
