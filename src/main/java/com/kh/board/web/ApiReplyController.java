package com.kh.board.web;

import com.kh.board.domain.board.svc.BoardSVC;
import com.kh.board.domain.entity.Board;
import com.kh.board.domain.entity.Reply;
import com.kh.board.domain.reply.svc.ReplySVC;
import com.kh.board.web.api.ApiResponse;
import com.kh.board.web.api.ResCode;
import com.kh.board.web.req.reply.ReqSave;
import com.kh.board.web.req.reply.ResSave;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiReplyController {

  private final BoardSVC boardSVC;
  private final ReplySVC replySVC;

  //
  @PostMapping
  @RequestMapping("/boards/{uid}/post/reply")
  public ApiResponse<?> add(@PathVariable("uid") Long uid,
                            @RequestBody ReqSave reqSave,
                            Model model) {
    log.info("reqSave={}", reqSave);

    Optional<Board> postId = boardSVC.findById(uid);
    Board board = postId.orElseThrow();
    model.addAttribute("board", board);

    Reply reply = new Reply();
    BeanUtils.copyProperties(reqSave, reply);
    Long userId = replySVC.rpSave(reply);

    ResSave resSave = new ResSave(userId, reqSave.getWriter(), reqSave.getCommentary());

    String rtDetail = "commentary : " + resSave;

    ApiResponse<ResSave> res = ApiResponse.createApiResponseDetail(ResCode.OK.getCode(), ResCode.OK.name(), rtDetail, resSave);

    return res;
  }
}
