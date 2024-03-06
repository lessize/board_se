package com.kh.board.domain.member.svc;

import com.kh.board.domain.entity.Member;

import java.util.Optional;

public interface MemberSVC {
  // 회원 가입
  Long joinMember(Member member);

  // 이메일 유무 확인
  boolean existEmail(String email);

  // 회원 조회
  Optional<Member> findByEmailPw(String email, String passwd);
}
