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
package me.ineson.demo.service.db.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity implementation class for Entity: Planet
 *
 */
@Entity(name="SolarBodyLink")
public class SolarBodyLink implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)   
    @Getter@Setter private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="solarBodyId")
    @Getter@Setter private SolarBody solarBody;
	
    @Column
    @Enumerated(EnumType.STRING)
    @Getter@Setter private SolarBodyLinkType linkType;

    @Column
    @Getter@Setter private String name;

    @Column
    @Getter@Setter private String url;
}
