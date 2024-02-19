package com.kh.board.domain.board.svc;

import com.kh.board.domain.board.dao.BoardDAO;
import com.kh.board.domain.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BoardSVCImpl implements BoardSVC {
  private BoardDAO boardDAO;
  BoardSVCImpl(BoardDAO boardDAO){
    this.boardDAO = boardDAO;
  }

  // 등록
  @Override
  public Long save(Board board) {
    return boardDAO.save(board);
  }

  // 목록
  @Override
  public List<Board> findAll() {
    return boardDAO.findAll();
  }

  //조회
  @Override
  public Optional<Board> findById(Long userId) {
    return boardDAO.findById(userId);
  }

  // 게시글 수정
  @Override
  public int updateById(Long userId, Board board) {
    return boardDAO.updateById(userId, board);
  }

  // 단건 삭제
  @Override
  public int deleteById(Long userId) {
    return boardDAO.deleteById(userId);
  }

  // 다중 삭제
  @Override
  public int deleteByIds(List<Long> userIds) {
    return boardDAO.deleteByIds(userIds);
  }
}
