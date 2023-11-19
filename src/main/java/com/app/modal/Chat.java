package com.app.modal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="Chats")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
