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
package me.ineson.demo.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import me.ineson.demo.service.db.domain.SolarBody;
import me.ineson.demo.service.db.repo.jpa.SolarBodyRepository;
import me.ineson.demo.service.db.repo.jpa.UserRepository;
import me.ineson.demo.service.db.repo.mongo.SuggestionRepository;
import me.ineson.demo.service.utils.UserHelper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * @author peter
 *
 */
@WebService(serviceName="SolarBodyService")
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL)
public class SolarBodyServiceImpl extends SpringBeanAutowiringSupport implements SolarBodyService {

    private static final Logger log = LoggerFactory.getLogger(SolarBodyServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SolarBodyRepository solarBodyRepository;

    @Autowired
    private SuggestionRepository suggestionRepository;

    private void validateInjection() {
        log.warn("Pre Validate injection, repo " + solarBodyRepository);
        if (solarBodyRepository == null) {
            log.warn("============================================= injecting");
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        }
        log.info("Post Validate injection, repo sb" + solarBodyRepository);
        log.info("Post Validate injection, repo sug" + suggestionRepository);
    }

    @WebMethod
    public List<Suggestion> listSuggestedUpdates(Long planetId) {
        validateInjection();
        // TODO: Rest API?
        log.info("Call Service listSuggestedUpdates, repo " + solarBodyRepository);
        log.info("List updated for Solar Bodies = " + solarBodyRepository.findOne(planetId));
        return null;
    }

    @WebMethod
    public Long countSuggestedUpdates() {
        //validateInjection();
        // TODO: Rest API?
        log.warn("Call Service countSuggestedUpdates, repo " + suggestionRepository);
        return suggestionRepository.count();
    }

    @WebMethod
    public void acceptSuggestedUpdate(String suggestionId, SolarBody planet) {
        validateInjection();

    }

    @WebMethod
    public void rejectSuggestedUpdate(String suggestionId) {
        validateInjection();

    }

    @Override
    public void submitSuggestedUpdate(me.ineson.demo.service.Suggestion arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void acceptSuggestedUpdate(String arg0, me.ineson.demo.service.SolarBody arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public User login(String username, String password) {
        me.ineson.demo.service.db.domain.User loginUser = null;
        log.debug( "Checkin login details of username {}", username);
        
        if( StringUtils.isNotBlank( username) && StringUtils.isNotBlank( password)) {
            loginUser = userRepository.findOne( me.ineson.demo.service.db.domain.User.GUEST_USERNAME);

            // If user was found, but the password was incorrect.
            if( loginUser != null && ! password.equals( loginUser.getPassword())) {
                log.debug( "User found, password did not match");
                loginUser = null;
            }
        }

        return UserHelper.convert(loginUser);
    }

    @Override
    public User getGuestUser() {
        
        me.ineson.demo.service.db.domain.User guestUser = userRepository.findOne( me.ineson.demo.service.db.domain.User.GUEST_USERNAME);
        log.info("Found guest user {}", guestUser);
        Assert.notNull(guestUser, "Guest user");

        User user = UserHelper.convert(guestUser);
        log.info("Converted guest user {}", user);
        return user;
    }

}
