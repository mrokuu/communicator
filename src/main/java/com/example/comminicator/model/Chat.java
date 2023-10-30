package com.example.comminicator.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chatName;
    private String chatImage;

    @ManyToMany
    private Set<User> admins=new HashSet<>();

    private Boolean is_group;

    @ManyToOne
    private User created_by;

    @ManyToMany
    @JoinTable(
            name = "chat_users",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @OneToMany
    private List<Message> messages=new ArrayList<>();



    @Override
    public String toString() {
        return "Chat [id=" + id + ", chat_name=" + chatName + ", chat_image=" + chatImage + ", admins=" + admins
                + ", is_group=" + is_group + ", created_by=" + created_by + ", users=" + users + ", messages="
                + messages + "]";
    }
}
