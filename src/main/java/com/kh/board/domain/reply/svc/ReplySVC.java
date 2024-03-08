package com.kh.board.domain.reply.svc;

import com.kh.board.domain.entity.Reply;

import java.util.List;

public interface ReplySVC {
  // 댓글 등록
  Long rpSave(Reply reply);

  // 댓글 목록
  List<Reply> findAll();

  // 댓글 수정
  public int updateByEmail(String email, Reply reply);

  // 총 레코드 건수
  int totalCnt();
}
