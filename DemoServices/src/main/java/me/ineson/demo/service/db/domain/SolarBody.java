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
import java.math.BigDecimal;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity implementation class for Entity: Planet
 *
 */
@Entity
@Table(name="SolarBody")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SolarBody implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Getter@Setter private Long id;

	@Column
	@Getter@Setter private String name;

	@XmlTransient
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="orbitSolarBodyId")
    @Getter@Setter private SolarBody orbitBody;

	@Column
    @Enumerated(EnumType.STRING)
    @Getter@Setter private SolarBodyType bodyType;
	
	@Column
    @Getter@Setter private String description;

	@Column
    @Getter@Setter private Long orbitDistance;

	@Column
    @Getter@Setter private Long radius;

	@Column
    @Getter@Setter private BigDecimal mass;
    
	@XmlTransient
	@JsonIgnore
	@OneToOne(fetch=FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @Getter@Setter private SolarBodyImage image;

	@XmlTransient
	@JsonIgnore
    @OneToMany(fetch=FetchType.LAZY, mappedBy="solarBody")
    @JoinColumn(name="orbitSolarBodyId")
    @Getter@Setter private List<SolarBodyLink> links;

    @Override
    public String toString() {
        return String.format("SolarBody [id=%s, name=%s, bodyType=%s]", id, name, bodyType);
    }
}
