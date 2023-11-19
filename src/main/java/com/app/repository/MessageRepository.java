package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.modal.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{
	
	@Query("select m from Message m join m.chat c where c.id=:chatId")
	public List<Message> findMessageByChatId(@Param("chatId") Long chatId);

}
