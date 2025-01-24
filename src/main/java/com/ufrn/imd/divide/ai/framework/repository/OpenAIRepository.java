package com.ufrn.imd.divide.ai.framework.repository;

import com.ufrn.imd.divide.ai.framework.model.Chat;
import com.ufrn.imd.divide.ai.framework.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenAIRepository extends JpaRepository<Chat, Long> {
    //    @Query(value = "SELECT c FROM Chat c WHERE c.userId = ?1 ORDER BY c.createdAt DESC LIMIT 1", nativeQuery = true)
    //    Chat findLatestChatByUserId(Long userId);
    Chat findTopByUserIdOrderByCreatedAtDesc(Long userId);
    Chat findTopByUserIdAndGroupIdOrderByCreatedAtDesc(Long userId, Long groupId);
    void deleteAllByGroup(Group group);
}
