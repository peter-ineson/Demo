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
package me.ineson.demo.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.ineson.demo.service.SolarBodyEndpointClient;
import me.ineson.demo.service.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author peter
 *
 */
public class SecurityInterceptor implements HandlerInterceptor {
    
    private static final Logger log = LoggerFactory.getLogger( SecurityInterceptor.class); 

    @Autowired
    private Config config;
 
    private SolarBodyEndpointClient SERVICE_ENDPOINT_CLIENT = new SolarBodyEndpointClient();
    
    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);

        SecurityContext securityContext = (SecurityContext) session.getAttribute( SecurityContext.ATTRIBUTE_NAME);
        if( securityContext == null) {
            log.debug( "No security context, loading context for guest user");
            log.debug( "config {}", config);
            String serviceUrl = config.getStringManadtory( Config.SERVICE_ENDPOINT_URL);
            log.debug( "serviceUrl {}", serviceUrl);
            User user = SERVICE_ENDPOINT_CLIENT.getGuestUser(serviceUrl);
            session.setAttribute( SecurityContext.ATTRIBUTE_NAME, new SecurityContext(user));
        }

        return true;
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }

}
