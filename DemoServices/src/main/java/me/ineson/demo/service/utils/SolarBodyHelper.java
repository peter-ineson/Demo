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

import me.ineson.demo.service.SolarBodyType;
import me.ineson.demo.service.db.domain.SolarBody;
import me.ineson.demo.service.db.domain.SolarBodyImage;

/**
 * Set of utilities for converting between DTO and DB object for the solar bodies.
 * @author peter
 * @version 1.0
 */
public class SolarBodyHelper {

    /**
     * Stop instantiation.
     */
    private SolarBodyHelper() {
    }

    /**
     * Converts from an {@linkplain Iterable} of {@linkplain SolarBody} to a {@linkplain List} of
     * {@linkplain me.ineson.demo.service.SolarBody}.
     * <p>
     * This function is null pointer safe and does not perform any validation check on the data. It also 
     * always returns a list, even it the input parameter is null.
     * 
     * @param dbSolarBodies Iterable of SolarBodies DB entities.
     * @return List Solar Body DTOs 
     */
    public static List<me.ineson.demo.service.SolarBody> convertList(Iterable<SolarBody> dbSolarBodies) {
        List<me.ineson.demo.service.SolarBody> results = new ArrayList<me.ineson.demo.service.SolarBody>();

        if (dbSolarBodies != null) {
            for (SolarBody dbSolarBody : dbSolarBodies) {
                results.add(convert(dbSolarBody));
            }
        }

        return results;
    }

    /**
     * Moves data from a {@linkplain SolarBody} entity to {@linkplain me.ineson.demo.service.SolarBody}
     * DTO. This function is null pointer safe, it just moves what data it can and does perform and
     * sort of validation.
     * 
     * @param dbSolarBody DB Entity.
     * @return Solar Body DTO.
     */
    public static me.ineson.demo.service.SolarBody convert(SolarBody dbSolarBody) {
        me.ineson.demo.service.SolarBody solarBody = null;
        if (dbSolarBody != null) {
            solarBody = new me.ineson.demo.service.SolarBody();
            solarBody.setId(dbSolarBody.getId());
            if( dbSolarBody.getBodyType() != null) {
                solarBody.setBodyType(SolarBodyType.fromValue(dbSolarBody.getBodyType().name()));
            }
            solarBody.setName(dbSolarBody.getName());
            solarBody.setDescription(dbSolarBody.getDescription());
            if (dbSolarBody.getOrbitBody() != null) {
                solarBody.setOrbitBodyId(dbSolarBody.getOrbitBody().getId());
            }
            solarBody.setOrbitDistance(dbSolarBody.getOrbitDistance());
            solarBody.setRadius(dbSolarBody.getRadius());
            solarBody.setMass(dbSolarBody.getMass());
            
            SolarBodyImage dbSolarBodyImage = dbSolarBody.getImage();
            if( dbSolarBodyImage != null) {
            	solarBody.setImageWidth( dbSolarBodyImage.getImageWidth());
            	solarBody.setImageHeight( dbSolarBodyImage.getImageHeight());
            }

        }

        return solarBody;
    }


    /**
     * Moves data from a {@linkplain me.ineson.demo.service.SolarBody} DTO to {@linkplain SolarBody} entity.
     * <p>
     * This function is null pointer safe, it just moves what data it can and does perform and
     * sort of validation.
     * <p>
     * This utility does not populate orbit body because an entity needs to be
     * looked up. It is left to the caller to populate this field 
     *
     * @param dtoSolarBody  DTO object
     * @return SolarBody Entity
     */
    public static SolarBody convert(me.ineson.demo.service.SolarBody dtoSolarBody) {
        SolarBody dbSolarBody = null;
        if (dtoSolarBody != null) {
            dbSolarBody = new SolarBody();
            dbSolarBody.setId(dtoSolarBody.getId());
            if( dtoSolarBody.getBodyType() != null) {
                dbSolarBody.setBodyType(me.ineson.demo.service.db.domain.SolarBodyType.valueOf(dtoSolarBody.getBodyType().value()));
            }
            dbSolarBody.setName(dtoSolarBody.getName());
            dbSolarBody.setDescription(dtoSolarBody.getDescription());
            dbSolarBody.setOrbitDistance(dtoSolarBody.getOrbitDistance());
            dbSolarBody.setRadius(dtoSolarBody.getRadius());
            dbSolarBody.setMass(dtoSolarBody.getMass());
        }

        return dbSolarBody;
    }

}
