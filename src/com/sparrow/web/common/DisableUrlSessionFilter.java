package com.sparrow.web.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sparrow.service.catalog.ProductServiceImpl;

/**
 * Servlet filter which disables URL-encoded session identifiers.
 *
 *
 * Copyright (c) 2006, Craig Condit. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS &quot;AS IS&quot;
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Modified by Devon Hillard (devon@digitalsanctuary.com) to only filter for GoogleBot,
 * not for users without cookies enabled.
 *
 */
@SuppressWarnings("deprecation")
public class DisableUrlSessionFilter implements Filter {

  /**
   * The string to look for in the User-Agent header to identify the GoogleBot.
   */
  private static final String GOOGLEBOT_AGENT_STRING = "googlebot";
  
  private static final String YAHOOBOT_AGENT_STRING = "slurp";
  
  private static final String MSNBOT_AGENT_STRING = "msnbot";

  /**
   * The request header with the User-Agent information in it.
   */
  private static final String USER_AGENT_HEADER_NAME = "User-Agent";
  
  private static final transient Log logger = LogFactory.getLog(DisableUrlSessionFilter.class);

  /**
   * Filters requests to disable URL-based session identifiers for bots.
   *
   * @param pRequest
   *                the request
   * @param pResponse
   *                the response
   * @param pChain
   *                the chain
   *
   * @throws IOException
   *                 Signals that an I/O exception has occurred.
   * @throws ServletException
   *                 the servlet exception
   */
  public void doFilter(final ServletRequest pRequest, final ServletResponse pResponse, final FilterChain pChain)
      throws IOException, ServletException {
    // skip non-http requests
    if (!(pRequest instanceof HttpServletRequest)) {
      pChain.doFilter(pRequest, pResponse);
      return;
    }

    HttpServletRequest httpRequest = (HttpServletRequest) pRequest;
    HttpServletResponse httpResponse = (HttpServletResponse) pResponse;

    boolean isBot = false;

    if (httpRequest != null) {
      String userAgent = httpRequest.getHeader(USER_AGENT_HEADER_NAME);
      if (StringUtils.isNotBlank(userAgent)) {
        if (userAgent.toLowerCase().indexOf(GOOGLEBOT_AGENT_STRING) > -1 
              || userAgent.toLowerCase().indexOf(YAHOOBOT_AGENT_STRING) > -1 
              || userAgent.toLowerCase().indexOf(MSNBOT_AGENT_STRING) > -1) {
          isBot = true;
          logger.warn(">>>>>>>> " + userAgent + "visited the site.");
        }
      }
    }

    if (isBot) {
      // wrap response to remove URL encoding
      HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpResponse) {
        @Override
        public String encodeRedirectUrl(final String url) {
          //logger.warn("Url is>>>>>>>> " + url);
          return url;
        }

        @Override
        public String encodeRedirectURL(final String url) {
          //logger.warn("Url is>>>>>>>> " + url);
          return url;
        }

        @Override
        public String encodeUrl(final String url) {
          //logger.warn("Url is>>>>>>>> " + url);
          return url;
        }

        @Override
        public String encodeURL(final String url) {
          //logger.warn("Url is>>>>>>>> " + url);
          return url;
        }
      };

      // process next request in chain
      pChain.doFilter(pRequest, wrappedResponse);
    } else {
      pChain.doFilter(pRequest, pResponse);
    }
  }

  /**
   * Unused.
   *
   * @param pConfig
   *                the config
   *
   * @throws ServletException
   *                 the servlet exception
   */
  public void init(final FilterConfig pConfig) throws ServletException {
  }

  /**
   * Unused.
   */
  public void destroy() {
  }
}
