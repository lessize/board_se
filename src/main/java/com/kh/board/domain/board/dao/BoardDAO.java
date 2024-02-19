package com.kh.board.domain.board.dao;

import com.kh.board.domain.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardDAO {
  // 등록
  Long save(Board board);

  // 목록
  List<Board> findAll();

  // 조회
  Optional<Board> findById(Long userId);

  // 수정
  int updateById(Long userId, Board board);

  // 단건 삭제
  int deleteById(Long userId);

  // 다중 삭제
  int deleteByIds(List<Long> userIds);
}
