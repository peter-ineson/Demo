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

import me.ineson.demo.service.SolarBodyLinkType;
import me.ineson.demo.service.db.domain.SolarBodyLink;

/**
 * Set of utilities for converting between DTO and DB objects for the solar bodie links.
 * @author peter
 * @version 1.0
 */
public class SolarBodyLinkHelper {

    /**
     * Stop instantiation.
     */
    private SolarBodyLinkHelper() {
    }

    /**
     * Converts from an {@linkplain Iterable} of {@linkplain SolarBodyLink} to a {@linkplain List} of
     * {@linkplain me.ineson.demo.service.SolarBodyLink}.
     * <p>
     * This function is null pointer safe and does not perform any validation check on the data. It also 
     * always returns a list, even it the input parameter is null.
     * 
     * @param dbSolarBodyLinks  Iterable of SolarBodyLinks DB entities.
     * @return List Solar Body Link DTOs 
     */
    public static List<me.ineson.demo.service.SolarBodyLink> convertList(Iterable<SolarBodyLink> dbSolarBodyLinks) {
        List<me.ineson.demo.service.SolarBodyLink> results = new ArrayList<me.ineson.demo.service.SolarBodyLink>();

        if (dbSolarBodyLinks != null) {
            for (SolarBodyLink dbLink : dbSolarBodyLinks) {
                results.add(convert(dbLink));
            }
        }

        return results;
    }

    /**
     * Moves data from a {@linkplain SolarBodyLink} entity to {@linkplain me.ineson.demo.service.SolarBodyLink}
     * DTO. This function is null pointer safe, it just moves what data it can and does perform and
     * sort of validation.
     * 
     * @param dbSolarBodyLink   DB Entity.
     * @return Solar Body Link DTO.
     */
    public static me.ineson.demo.service.SolarBodyLink convert(SolarBodyLink dbSolarBodyLink) {
        me.ineson.demo.service.SolarBodyLink solarBodyLink = null;
        if (dbSolarBodyLink != null) {
            solarBodyLink = new me.ineson.demo.service.SolarBodyLink();
            solarBodyLink.setId(dbSolarBodyLink.getId());
            if (dbSolarBodyLink.getSolarBody() != null) {
                solarBodyLink.setSolarBodyId(dbSolarBodyLink.getSolarBody().getId());
            }
            if( dbSolarBodyLink.getLinkType() != null) {
                solarBodyLink.setLinkType(SolarBodyLinkType.fromValue(dbSolarBodyLink.getLinkType().name()));
            }
            solarBodyLink.setName(dbSolarBodyLink.getName());
            solarBodyLink.setUrl(dbSolarBodyLink.getUrl());
        }

        return solarBodyLink;
    }


    /**
     * Moves data from a {@linkplain me.ineson.demo.service.SolarBodyLink} DTO to {@linkplain SolarBodyLink} entity.
     * <p>
     * This function is null pointer safe, it just moves what data it can and does perform and
     * sort of validation.
     * <p>
     * This utility does not populate Solar Body field because an entity needs to be
     * looked up. It is left to the caller to populate this field 
     *
     * @param dtoSolarBodyLink  DTO
     * @return SolarBodyLink Entity
     */
    public static SolarBodyLink convert(me.ineson.demo.service.SolarBodyLink dtoSolarBodyLink) {
        SolarBodyLink dbSolarBodLinky = null;
        if (dtoSolarBodyLink != null) {
            dbSolarBodLinky = new SolarBodyLink();
            dbSolarBodLinky.setId(dtoSolarBodyLink.getId());
            if( dtoSolarBodyLink.getLinkType() != null) {
                dbSolarBodLinky.setLinkType(me.ineson.demo.service.db.domain.SolarBodyLinkType.valueOf(dtoSolarBodyLink.getLinkType().value()));
            }
            dbSolarBodLinky.setName(dtoSolarBodyLink.getName());
            dbSolarBodLinky.setUrl(dtoSolarBodyLink.getUrl());
        }

        return dbSolarBodLinky;
    }

}
