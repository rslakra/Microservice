package com.rslakra.microservice.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * It is the class where all the APIs getting flittered. We extended one class named by "ZuulFilter".
 * <p>
 * Four methods will be override from this class.
 * <p>
 * filterType(): The interruption time of a request will be decided in this method. pre key is used to filter before
 * reaching the Server application . post key is used to filter when response came back from the Server application.
 * error key is used to filter any error happened.
 * <p>
 * filterOrder(): To set the priority of the filter process.
 * <p>
 * shouldFilter(): To decide whether the request is filter or not.
 * <p>
 * run(): This method will trigger after filtering process. So that we can write the business logic what ever we
 * required.
 * <p>
 * After running this application, the instance of this application will be appear in the Eureka server dashboard.
 *
 * @author Rohtash Lakra
 * @created 3/2/24 12:09 PM
 */
//@RestController
//@RequestMapping("/client")
@Component
public class ZuulLoginFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZuulLoginFilter.class);

    /**
     * The interruption time of a request will be decided in this method.
     * <pre>
     *  <code>pre</code> key is used to filter before reaching the Server application .
     *  <code>post</code> key is used to filter when response came back from the Server application.
     *  <code>error</code> key is used to filter any error happened.
     * </pre>
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * To set the priority of the filter process.
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * To decide whether the request is filter or not.
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * This method will trigger after filtering process. So that we can write the business logic what ever we required.
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        LOGGER.debug("+run()");
        LOGGER.debug("Request is filtered!");
        HttpServletRequest servletRequest = RequestContext.getCurrentContext().getRequest();
        LOGGER.debug("servletRequest:{}, requestURI:{}", servletRequest, servletRequest.getRequestURI());
        LOGGER.debug("-run()");
        return null;
    }
}
