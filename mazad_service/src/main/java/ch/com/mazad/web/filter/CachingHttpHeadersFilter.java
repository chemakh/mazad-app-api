package ch.com.mazad.web.filter;

import ch.com.mazad.config.MazadProperties;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class CachingHttpHeadersFilter implements Filter
{

    private final static long LAST_MODIFIED = System.currentTimeMillis();

    private long cacheTimeToLive = TimeUnit.DAYS.toMillis(1461L);

    private MazadProperties mazadProperties;

    public CachingHttpHeadersFilter(MazadProperties mazadProperties)
    {
        this.mazadProperties = mazadProperties;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        cacheTimeToLive = TimeUnit.DAYS.toMillis(mazadProperties.getHttp().getCache().getTimeToLiveInDays());
    }

    @Override
    public void destroy()
    {
        // Nothing to destroy
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader("Cache-Control", "max-age=" + cacheTimeToLive + ", public");
        httpResponse.setHeader("Pragma", "cache");

        // Setting Expires header, for proxy caching
        httpResponse.setDateHeader("Expires", cacheTimeToLive + System.currentTimeMillis());

        // Setting the Last-Modified header, for browser caching
        httpResponse.setDateHeader("Last-Modified", LAST_MODIFIED);

        chain.doFilter(request, response);
    }
}
