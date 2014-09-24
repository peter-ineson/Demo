/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.ineson.demo.service.rest;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.spi.ExtendedExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An exception mapper for logging exceptions that occur
 * @author peter
 *
 */
@Provider
public class ExceptionLogger implements ExtendedExceptionMapper<Exception>{
    
    private static final Logger log = LoggerFactory.getLogger(ExceptionLogger.class);

    @Context
    HttpServletRequest request;
    
    @Override
    public boolean isMappable(Exception exception) {
        // Log the exception, and return false so the standard handlers display the error in the response.  
        log.error(buildLoggingMessage(), exception);
        return false;
    }

    @Override
    public Response toResponse(Exception exception) {
        log.error(buildLoggingMessage(), exception);
        throw new WebApplicationException(exception);
    }

    private String buildLoggingMessage() {
        StringBuffer message = new StringBuffer("Error calling rest service");
        if (request != null) {
            message.append("\nRequest: ").append(getOriginalURL(request));
            message.append("\nAccept: ").append(request.getHeader("Accept"));
            String method = request.getMethod();
            message.append("\nMethod: ").append(request.getMethod());
            if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
                message.append("\nContent-type: ").append(request.getContentType());
                String content = "(Error occured)";
                try (Scanner scanner = new Scanner(request.getInputStream(), "UTF-8")) {
                    Scanner delimitedScanner = scanner.useDelimiter("\\A");
                    content = delimitedScanner.hasNext() ? delimitedScanner.next() : content;
                } catch (IOException e) {
                    // Ignore and contimue.
                }

                message.append("\nContent;\n").append(content);
            }
        }
        return message.toString();
    }

    private String getOriginalURL(HttpServletRequest req) {
        String scheme = req.getScheme();           // http
        String serverName = req.getServerName();   // hostname.com
        int serverPort = req.getServerPort();      // 80
        String contextPath = req.getContextPath(); // /mywebapp
        String servletPath = req.getServletPath(); // /servlet/MyServlet
        String pathInfo = req.getPathInfo();       // /a/b;c=123
        String queryString = req.getQueryString(); // d=789

        // Reconstruct original requesting URL
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath).append(servletPath);

        if (pathInfo != null) {
            url.append(pathInfo);
        }

        if (queryString != null) {
            url.append("?").append(queryString);
        }

        return url.toString();
    }
    
}
