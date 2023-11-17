package com.app.modal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String fullName;
	private String email;
	private String profile_picture;
	private String password;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Notification> notifications=new ArrayList<>();
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", full_name=" + fullName + ", email=" + email + ", notifications=" + notifications
				+ "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email, fullName, id, password, profile_picture);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(id, other.id) && Objects.equals(password, other.password)
				&& Objects.equals(profile_picture, other.profile_picture);
	}


	
	
	
	

}
