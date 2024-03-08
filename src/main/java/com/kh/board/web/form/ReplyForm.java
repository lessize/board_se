package com.kh.board.web.form;

import lombok.Data;

import java.time.LocalDateTime;

public class ReplyForm {
  private Long replyId;              // number(5), -- 아이디
  private Long userId;               // number(5), -- 원글번호
  private String commentary;         // clob,      -- 내용
  private String writer;             // varchar2(30),  -- 작성자
  private String usermail;           // varchar2(50) -- 이메일
}
