package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.modal.Chat;
import com.app.modal.User;

public interface ChatRepository extends JpaRepository<Chat, Long> {

	@Query("select c from Chat c join c.users u where u.id=:userId")
	public List<Chat> findChatByUserId(Long userId);
	
	@Query("select c from Chat c Where c.is_group=false And :user Member of c.users And :reqUser Member of c.users")
	public Chat findSingleChatByUsersId(@Param("user")User user, @Param("reqUser")User reqUser);
}
