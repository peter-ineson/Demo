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
package me.ineson.demo.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import me.ineson.demo.service.db.domain.SolarBodyType;
import me.ineson.demo.service.rest.SolarBodyEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * Suite of utilities for helping the rest services. 
 * 
 * @author peter
 */
public class RestUtils {

    private final static Logger log = LoggerFactory.getLogger(SolarBodyEndpoint.class);
    
    /**
     * Stop instantiation.
     */
    private RestUtils() {
    }

    /**
     * @param where
     * @param root
     * @param query
     * @param builder
     * @param translations
     * @return
     */
    public static Predicate parseWhereClause( String where, Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder,
            Map<String, String>translations) {
        
        List<Predicate> predicates = new ArrayList<Predicate>();
        for (String singleCriteria : new StrTokenizer(where, ",").getTokenList()) {
            if (StringUtils.isNotBlank(singleCriteria)) {
                int equalsIndex = singleCriteria.indexOf("=");
                if (equalsIndex > 0) {
                    String fieldPath = singleCriteria.substring(0, equalsIndex);
                    String value = singleCriteria.substring(equalsIndex + 1);
                    
                    if( translations != null && translations.containsKey( fieldPath)) {
                        String newFieldPath = translations.get( fieldPath);
                        log.debug( "replacing field {} with {} ", fieldPath, newFieldPath );
                        fieldPath = newFieldPath;
                    }

                    StrTokenizer tokenizer = new StrTokenizer(fieldPath, ".");

                    javax.persistence.criteria.Path<?> expression = null;
                    while (tokenizer.hasNext()) {
                        String field = tokenizer.next();
                        if (tokenizer.hasNext()) {
                            if (expression == null) {
                                expression = root.join(field);
                            } else {
                                // expression = expression.join( field);
                                throw new IllegalArgumentException(
                                        "Paths to joins of greater than a depth of 1 are not implemented yet");
                            }
                        } else {
                            if (expression == null) {
                                log.info( "expression0 {}", expression);
                                expression = root.get(field);
                                log.info( "expression1 {}", expression);
                            } else {
                                expression = expression.get(field);
                            }
                        }
                    }

                    
                    Object realValue = value;
                    if( "bodyType".equals(fieldPath)) {
                        me.ineson.demo.service.SolarBodyType solarBodyType = me.ineson.demo.service.SolarBodyType.valueOf(value);
                        switch (solarBodyType) {
                        case PLANET:
                            realValue = SolarBodyType.Planet;
                            break;

                        case SUN:
                            realValue = SolarBodyType.Sun;
                            break;

                        case DWARF_PLANET:
                            realValue = SolarBodyType.DwarfPlanet;
                            break;

                        default:
                            realValue = solarBodyType;
                        }
                        log.info( "enum bodyType before {} after {}", value, realValue);
                    }
                    
                    log.info( "expression9 {}", expression);
                    predicates.add(builder.equal(expression, realValue));
                }

            }
        }

        log.debug( "predictes " );
        if( predicates.size() == 0) {
            return null;
        } if( predicates.size() == 1) {
            return predicates.get(0);
        }
        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    /**
     * @param orderBy
     * @return
     */
    public static Sort parseSortOrder( String orderBy) {
        Sort sort = null;
        
        if (StringUtils.isNotEmpty(orderBy)) {
            List<Order> orderByList = new ArrayList<>();
            for (String singleOrderBy : new StrTokenizer(orderBy, ",").getTokenList()) {
                log.trace("singleOrderBy {}", singleOrderBy);
                if (StringUtils.isNotBlank(singleOrderBy)) {
                    Direction direction = Direction.ASC;
                    if (singleOrderBy.startsWith("+")) {
                        singleOrderBy = singleOrderBy.substring(1);
                    } else if (singleOrderBy.startsWith("-")) {
                        direction = Direction.DESC;
                        singleOrderBy = singleOrderBy.substring(1);
                    }
                    orderByList.add(new Order(direction, singleOrderBy));
                }
            }
            if (!orderByList.isEmpty()) {
                sort = new Sort(orderByList);
            }
        }
        
 
        return sort;
    }
 
}
