package com.kh.board.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Board {
  private Long userId;              // user_id  number(5),      -- 아이디
  private String title;             // title    varchar2(30),   -- 제목
  private String material;          // material clob,           -- 내용
  private String writer;            // writer   varchar2(30),   -- 작성자
  private LocalDateTime cdate;      // cdate    timestamp,      -- 작성일자
  private LocalDateTime udate;      // udate    timestamp       -- 수정일자
}
