package myPackage.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import myPackage.config.VkConfig;
import myPackage.entities.VkCallbackRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

@Component
public class VkSecretKeyInterceptor implements HandlerInterceptor {

    @Autowired
    private VkConfig vkConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equalsIgnoreCase("POST")) {
            GlobalWrapFilter.MultiReadRequest multiReadRequest = new GlobalWrapFilter.MultiReadRequest(request);

            if (multiReadRequest.getBody().trim().equals("")) {
                return false;
            }

            ObjectMapper mapper = new ObjectMapper();
            VkCallbackRequest callback = mapper.readValue(multiReadRequest.getBody(), VkCallbackRequest.class);

            return callback.getSecret().equals(vkConfig.getSecret());
        }
        return true;
    }

    @Component
    @Order(Ordered.HIGHEST_PRECEDENCE)
    private static class GlobalWrapFilter implements Filter {

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            MultiReadRequest wrapper = new MultiReadRequest((HttpServletRequest) request);
            chain.doFilter(wrapper, response);
        }

        private static class MultiReadRequest extends HttpServletRequestWrapper {

            private String body;

            public String getBody() {
                return body;
            }

            public MultiReadRequest(HttpServletRequest request) throws IOException {
                super(request);
                body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
                return new ServletInputStream() {
                    @Override
                    public boolean isFinished() {
                        return byteArrayInputStream.available() == 0;
                    }

                    @Override
                    public boolean isReady() {
                        return true;
                    }

                    @Override
                    public void setReadListener(ReadListener readListener) {
                    }

                    @Override
                    public int read() throws IOException {
                        return byteArrayInputStream.read();
                    }
                };
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return new BufferedReader(new InputStreamReader(this.getInputStream(), Charset.forName("UTF-8")));
            }

        }

    }

}
