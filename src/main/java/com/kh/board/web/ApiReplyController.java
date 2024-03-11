package com.kh.board.web;

import com.kh.board.domain.board.svc.BoardSVC;
import com.kh.board.domain.entity.Board;
import com.kh.board.domain.entity.Reply;
import com.kh.board.domain.reply.svc.ReplySVC;
import com.kh.board.web.api.ApiResponse;
import com.kh.board.web.api.ResCode;
import com.kh.board.web.req.reply.ReqSave;
import com.kh.board.web.req.reply.ReqUpdate;
import com.kh.board.web.req.reply.ResSave;
import com.kh.board.web.req.reply.ResUpdate;
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

  // 등록
  @PostMapping
  public ApiResponse<ResSave> addReply(@PathVariable("uid") Long userId,
                                       @RequestBody ReqSave reqSave) {
    log.info("ReqSave={}", reqSave);

    // 댓글 생성 및 데이터 복사
    Reply reply = new Reply();
    BeanUtils.copyProperties(reqSave, reply);
    reply.setUserId(userId); // 댓글이 속한 게시글 ID 설정
    reply.setCdate(LocalDateTime.now()); // 작성일자 설정

    // 댓글 저장
    Long replyId = replySVC.rpSave(reply);

    // 응답 생성
    ResSave resSave = new ResSave(replyId, reqSave.getWriter(), reqSave.getCommentary(), reqSave.getUsermail());
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

  // 수정
  @PatchMapping("/{rpNum}")
  public ApiResponse<?> update (@PathVariable("uid") Long userId,
                                @PathVariable("rpNum") Long replyId,
                                @RequestBody ReqUpdate reqUpdate) {
    String sessionEmail = reqUpdate.getUsermail();
    log.info(sessionEmail);
    return null;
  }

  // 삭제
  @DeleteMapping("/{rid}")
  public ApiResponse<?> deleteById(@PathVariable("uid") Long userId,
                                   @PathVariable("rid") Long replyId) {
    int deletedCnt = replySVC.deleteById(userId, replyId);

    ApiResponse<ResUpdate> res = null;

    if (deletedCnt == 1) {
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), null);
    } else {
      res = ApiResponse.createApiResponse(ResCode.FAIL.getCode(), ResCode.FAIL.name(), null);
    }

    return res;
  }
}
