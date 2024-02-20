package com.kh.board.domain.board.dao;

import com.kh.board.domain.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class BoardDAOImplTest {

  @Autowired
  BoardDAO boardDAO;

  @Test
  @DisplayName("게시글 등록")
  void save() {
    Board board = new Board();
    board.setTitle("new title");
    board.setWriter("LUKAS");
    board.setMaterial("LOOOOOREM");

    Long userId = boardDAO.save(board);
  }

  @Test
  @DisplayName("게시글 목록")
  void findAll() {
    List<Board> list = boardDAO.findAll();
    for (Board board : list) {
      log.info("product={}", board);
    }
    log.info("size={}", list.size());
  }

  @Test
  @DisplayName("게시글 조회")
  void findById() {
    Long userId = 1L;
    Optional<Board> findBoard = boardDAO.findById(userId);
    Board board = findBoard.orElse(null);
    log.info("board={}", board);
  }

  @Test
  @DisplayName("게시글 수정")
  void updateById() {
    Long userId = 1L;
    Board board = new Board();
    board.setTitle("changed");
    board.setWriter("ALICE");
    board.setMaterial("Have it changed?");
    int updatedRowCnt = boardDAO.updateById(userId, board);
    if (updatedRowCnt == 0) {
      Assertions.fail("변경 내역이 없습니다.");
    }
    Optional<Board> optionalBoard = boardDAO.findById(userId);
    if (optionalBoard.isPresent()) {
      Board findedBoard = optionalBoard.get();
      Assertions.assertThat(findedBoard.getTitle()).isEqualTo("changed");
      Assertions.assertThat(findedBoard.getWriter()).isEqualTo("ALICE");
      Assertions.assertThat(findedBoard.getMaterial()).isEqualTo("Have it changed?");
    } else {
      Assertions.fail("변경할 상품이 없습니다.");
    }
  }

  @Test
  @DisplayName("단건 삭제")
  void deleteById() {
    Long uid = 1L;
    int deletedRowCnt = boardDAO.deleteById(uid);
    Assertions.assertThat(deletedRowCnt).isEqualTo(1);
  }

  @Test
  @DisplayName("다중 삭제")
  void deleteByIds() {
    List<Long> ids = new ArrayList<>();
    ids.add(1L);
    ids.add(2L);
    int deletedRowCnt = boardDAO.deleteByIds(ids);

    Assertions.assertThat(deletedRowCnt).isEqualTo(ids.size());
  }
}