package com.kh.board.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reply {
  private Long replyId;             // number(5), -- 아이디
  private Long userId;              // number(5), -- 원글번호
  private String commentary;         // clob,      -- 내용
  private String writer;             // varchar2(30),  -- 작성자
  private LocalDateTime cdate;       // timestamp, -- 작성일자
  private LocalDateTime udate;       // timestamp -- 수정일자
}
