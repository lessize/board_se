package com.kh.board.domain.reply.dao;

import com.kh.board.domain.entity.Reply;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

//  @Test
//  void findAll() {
//    List<Reply> list = replyDAO.findAll(1L,1L, 5L);
//    for (Reply reply : list) {
//      log.info("reply={}", reply);
//    }
//    log.info("size={}", list.size());
//  }

  @Test
  void testFindAll() {
    List<Reply> list = replyDAO.findAll();
    for (Reply reply : list) {
      log.info("reply={}", reply);
    }
    log.info("size={}", list.size());
  }

  @Test
  @Transactional
  void updateByEmail() {
    String email = "user3@kh.com";
    Reply reply = new Reply();
    reply.setCommentary("changed commentary");
    reply.setUsermail(email);

    log.info("reply={}", reply);
  }
}