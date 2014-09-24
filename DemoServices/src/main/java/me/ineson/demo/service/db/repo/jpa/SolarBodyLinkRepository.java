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
package me.ineson.demo.service.db.repo.jpa;

import me.ineson.demo.service.db.domain.SolarBodyLink;
import me.ineson.demo.service.db.domain.SolarBodyLinkType;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author peter
 *
 */
@Transactional(readOnly = true)
public interface SolarBodyLinkRepository extends CrudRepository<SolarBodyLink, Long> {

    @Query("SELECT o FROM SolarBodyLink o WHERE o.solarBody.id = :solarBodyId")
    Iterable<SolarBodyLink> findBySolarBodyId(@Param("solarBodyId") Long solarBodyId);

    @Query("SELECT o FROM SolarBodyLink o WHERE o.solarBody.id = :solarBodyId AND o.linkType = :linkType")
    Iterable<SolarBodyLink> findBySolarBodyIdAndType(
            @Param("solarBodyId") Long solarBodyId,
            @Param("linkType") SolarBodyLinkType linkType);
}
