package com.kh.board.web;

import com.kh.board.domain.board.svc.BoardSVC;
import com.kh.board.domain.entity.Board;
import com.kh.board.web.board.UpdateForm;
import com.kh.board.web.board.WritingForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequestMapping("/boards")
public class BoardController {
  private BoardSVC boardSVC;

  BoardController(BoardSVC boardSVC) {
    this.boardSVC = boardSVC;
  }

  // 게시글 작성
  @GetMapping("/writing")
  public String writingForm(Model model) {
    model.addAttribute("writingForm", new WritingForm());
    return "board/writing";
  }

  // 게시글 등록
  @PostMapping("/writing")
  public String writing(WritingForm writingForm,
                        Model model,
                        RedirectAttributes redirectAttributes) {

    // 유효성 체크
    String pattern = "^[0-9a-zA-Zㄱ-ㅎ가-힣 ]{1,10}$";
    // 제목
    if (!Pattern.matches(pattern, writingForm.getTitle())) {
      model.addAttribute("writingForm", writingForm);
      model.addAttribute("s_err_title", "영문/숫자/한글 1~10 자리 입력");
      return "board/writing";
    }
    // 작성자
    pattern = "^[a-zA-Zㄱ-ㅎ가-힣 ]{1,10}$";
    if (!Pattern.matches(pattern, writingForm.getWriter())) {
      model.addAttribute("writingForm", writingForm);
      model.addAttribute("s_err_writer", "영문/한글 1~10 자리 입력");
      return "board/writing";
    }
    // 내용
    pattern = "[\\s\\S]*{1,}";
    if (!Pattern.matches(pattern, writingForm.getMaterial())) {
      model.addAttribute("writingForm", writingForm);
      model.addAttribute("s_err_material", "한 글자 이상 입력");
      return "board/writing";
    }

      Board board = new Board();
      board.setTitle(writingForm.getTitle());
      board.setWriter(writingForm.getWriter());
      board.setMaterial(writingForm.getMaterial());

      Long userId = boardSVC.save(board);
      redirectAttributes.addAttribute("uid", userId);

      return "redirect:/boards/{uid}/post";
  }

  // 게시글 목록
  @GetMapping("")
  public String postList(Model model) {
    List<Board> list = boardSVC.findAll();
    model.addAttribute("list", list);

    return "board/list";
  }

  // 게시글 조회
  @GetMapping("/{uid}/post")
  public String findById(@PathVariable("uid") Long userId, Model model) {
    Optional<Board> findedBoard = boardSVC.findById(userId);

    Board board = findedBoard.orElseThrow();
    model.addAttribute("board", board);

    return "board/posting";
  }

  // 게시글 수정 폼
  @GetMapping("/{uid}/edit")
  public String updatePost(@PathVariable("uid") Long userId,
                           Model model
  ) {
    Optional<Board> optionalBoard = boardSVC.findById(userId);
    Board findedBoard = optionalBoard.orElseThrow();
    model.addAttribute("board", findedBoard);

    return "board/update";
  }

  // 게시글 수정 내역 저장
  @PostMapping("/{uid}/edit")
  public String update(@PathVariable("uid") Long userId,
                       UpdateForm updateForm,
                       RedirectAttributes redirectAttributes,
                       Model model) {
    // 유효성 체크
    String pattern = "^[0-9a-zA-Zㄱ-ㅎ가-힣 ]{1,10}$";
    // 제목
    if (!Pattern.matches(pattern, updateForm.getTitle())) {
      model.addAttribute("updateForm", updateForm);
      model.addAttribute("s_err_title", "영문/숫자/한글 1~10 자리 입력");
      return "board/update";
    }
    // 내용
    pattern = "[\\s\\S]{1,}";
    if (!Pattern.matches(pattern, updateForm.getMaterial())) {
      model.addAttribute("updateForm", updateForm);
      model.addAttribute("s_err_material", "한 글자 이상 입력");
      return "board/update";
    }

    Board board = new Board();
    board.setTitle(updateForm.getTitle());
    board.setMaterial(updateForm.getMaterial());

    boardSVC.updateById(userId, board);

    redirectAttributes.addAttribute("uid", userId);
    return "redirect:/boards/{uid}/post";
  }

  // 게시글 단건 삭제
  @GetMapping("/{uid}/delete")
  public String deleteById(@PathVariable("uid") Long userId) {
    int deletedPost = boardSVC.deleteById(userId);
    return "redirect:/boards";
  }

  // 게시글 다중 삭제
  @PostMapping("/delete")
  public String deleteByIds (@RequestParam("uids") List<Long> uids) {
    int deletedPosts = boardSVC.deleteByIds(uids);

    return "redirect:/boards";
  }
}
