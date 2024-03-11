package com.kh.board.domain.reply.svc;

import com.kh.board.domain.entity.Reply;
import com.kh.board.domain.reply.dao.ReplyDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplySVCImpl implements ReplySVC{

  private final ReplyDAO replyDAO;

  @Override
  public Long rpSave(Reply reply) {
    return replyDAO.rpSave(reply);
  }

  @Override
  public List<Reply> findAll() {
    return replyDAO.findAll();
  }

  @Override
  public int updateByEmail(Long replyId, Reply reply) {
    return replyDAO.updateByEmail(replyId, reply);
  }

  @Override
  public int deleteById(Long userId, Long replyId) {
    return replyDAO.deleteById(userId, replyId);
  }

  @Override
  public int totalCnt() {
    return replyDAO.totalCnt();
  }
}
