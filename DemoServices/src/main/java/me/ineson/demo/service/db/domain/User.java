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
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity(name="User")
public class User implements Serializable {

    public static final String GUEST_USERNAME = "guest";

	private static final long serialVersionUID = 1L;

	@Id
	@Getter@Setter private String username;

    @Column
    @Getter@Setter private String password;
	
	@Column
	@Getter@Setter private String name;

	@Column
    @Enumerated(EnumType.STRING)
    @Getter@Setter private UserRole role;
}
