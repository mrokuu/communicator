package com.example.comminicator.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String fullName;
    private String email;
    private String profilePicture;


    @Override
    public int hashCode() {
        return Objects.hash(email, fullName, id, profilePicture);
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
                && Objects.equals(id, other.id) && Objects.equals(profilePicture, other.profilePicture);


    }
}
