package com.kh.board.domain.reply.svc;

import com.kh.board.domain.entity.Reply;

import java.util.List;

public interface ReplySVC {
  // 댓글 등록
  Long rpSave(Reply reply);

  // 댓글 목록
  List<Reply> findAll();

//  // 댓글 목록
//  List<Reply> findAll(Long userId, Long reqPage, Long recCnt);

  // 총 레코드 건수
  int totalCnt();
}
