package com.kh.board.domain.member.svc;

import com.kh.board.domain.entity.Member;
import com.kh.board.domain.member.dao.MemberDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC {

  private final MemberDAO memberDAO;

  @Override
  public Long joinMember(Member member) {
    return memberDAO.insertMember(member);
  }

  @Override
  public boolean existEmail(String email) {
    return memberDAO.existEmail(email);
  }

  @Override
  public Optional<Member> findByEmailPw(String email, String passwd) {
    return memberDAO.findByEmailPw(email, passwd);
  }
}
