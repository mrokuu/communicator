package com.example.comminicator.model;

import jakarta.persistence.*;
import lombok.*;

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
    private Boolean isGroup;

    @ManyToOne
    private User createdBy;



    @Override
    public String toString() {
        return "Chat [id=" + id + ", chat_name=" + chatName + ", chat_image=" + chatImage
                + ", is_group=" + isGroup + ", created_by=" + createdBy + ", users=";
    }
}
