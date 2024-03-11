package com.kh.board.web.req.reply;

import lombok.Data;

@Data
public class ResUpdate {
  private Long userId;
  private Long replyId;
  private String commentary;
  private String usermail;
}
