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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{uid}/reply")
public class ApiReplyController {

  private final ReplySVC replySVC;

  @PostMapping
  public ApiResponse<ResSave> addReply(@PathVariable("uid") Long userId,
                                       @RequestBody ReqSave reqSave) {
    log.info("Request to add reply: {}", reqSave);

    // 댓글 생성 및 데이터 복사
    Reply reply = new Reply();
    BeanUtils.copyProperties(reqSave, reply);
    reply.setUserId(userId); // 댓글이 속한 게시글 ID 설정
    reply.setCdate(LocalDateTime.now()); // 작성일자 설정

    // 댓글 저장
    Long replyId = replySVC.rpSave(reply);

    // 응답 생성
    ResSave resSave = new ResSave(replyId, reqSave.getWriter(), reqSave.getCommentary());
    return ApiResponse.createApiResponseDetail(ResCode.OK.getCode(), ResCode.OK.name(), "댓글이 성공적으로 저장되었습니다.", resSave);
  }

    // 목록
    @GetMapping
    public ApiResponse<List<Reply>> list(@PathVariable("uid") Long userId) {

      List<Reply> list = replySVC.findAll();

      ApiResponse<List<Reply>> response = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), list);
      response.setTotalCnt(list.size());
      return response;
    }
}
