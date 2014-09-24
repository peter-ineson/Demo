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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import lombok.Getter;
import lombok.Setter;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

/**
 * Entity implementation class for Entity: Planet
 *
 */
@Entity(name="SolarBodyImage")
@XmlAccessorType(XmlAccessType.FIELD)
public class SolarBodyImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @Getter@Setter private Long solarBodyId;
	
    @XmlInverseReference(mappedBy="image")
    @MapsId 
    @OneToOne(fetch=FetchType.LAZY, mappedBy="image")
    @JoinColumn(name = "solarBodyId")    
	@Getter@Setter private SolarBody solarBody;

	@Column( nullable=false)
    @Getter@Setter private byte image[];

    @Column( nullable=false)
    @Getter@Setter private String filename;

    @Column( nullable=false)
    @Getter@Setter private String contentType;
}
