package com.kh.board.domain.reply.dao;

import com.kh.board.domain.entity.Reply;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ReplyDAOImplTest {

  @Autowired
  ReplyDAO replyDAO;

  @Test
  @Transactional
  void rpSave() {
    Reply reply = new Reply();
    reply.setUserId(1L);
    reply.setCommentary("테스트 댓글");
    reply.setWriter("테스터");

    // When
    Long replyId = replyDAO.rpSave(reply);

    log.info("replyId={}", replyId);
  }
}