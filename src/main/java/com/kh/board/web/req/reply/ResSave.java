package com.kh.board.web.req.reply;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResSave {
  private Long userId;
  private String writer;
  private String commentary;
}
