package com.kh.board.domain.reply.dao;

import com.kh.board.domain.entity.Reply;

public interface ReplyDAO {

  // 댓글 등록
  Long rpSave(Reply reply);
}
