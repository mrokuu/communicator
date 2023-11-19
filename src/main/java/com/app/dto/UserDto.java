package com.app.dto;

import lombok.*;

import java.util.Objects;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private Long id;
	private String fullName;
	private String email;
	private String profile_picture;
	
	

	@Override
	public int hashCode() {
		return Objects.hash(email, fullName, id, profile_picture);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		return Objects.equals(email, other.email) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(id, other.id) && Objects.equals(profile_picture, other.profile_picture);
	}
	
	
	

}
