package com.kh.board.domain.reply.svc;

import com.kh.board.domain.entity.Reply;

public interface ReplySVC {
  // 댓글 등록
  Long rpSave(Reply reply);
}
