package com.kh.board.domain.member.dao;

import com.kh.board.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberDAOImplTest {

  @Autowired
  MemberDAO memberDAO;

  @Test
  @DisplayName("회원가입")
  void insertMember() {
    Member member = new Member();
    member.setEmail("user6@kh.com");
    member.setPasswd("user6");
    member.setNickname("사용자6");

    Long memberId = memberDAO.insertMember(member);
    log.info("memberId={}", memberId);
  }

  @Test
  @DisplayName("이메일 있음")
  void existEmail() {
    boolean exist = memberDAO.existEmail("user1@kh.com");
    Assertions.assertThat(exist).isEqualTo(true);
  }

  @Test
  @DisplayName("이메일 없음")
  void notExistEmail() {
    boolean exist = memberDAO.existEmail("15420@kh.com");
    Assertions.assertThat(exist).isEqualTo(false);
  }

  @Test
  @DisplayName("회원 있음")
  void findByEmailPw() {
    Optional<Member> optionalMember = memberDAO.findByEmailPw("user1@kh.com", "user1");

    Assertions.assertThat(optionalMember).isPresent();
    Member member = optionalMember.get();
    Assertions.assertThat(member.getEmail()).isEqualTo("user1@kh.com");
    Assertions.assertThat(member.getPasswd()).isEqualTo("user1");
  }

  @Test
  @DisplayName("회원 없음")
  void notfindByEmailPw() {
    Optional<Member> optionalMember = memberDAO.findByEmailPw("323434@kh.com", "4231");

    Assertions.assertThat(optionalMember).isEmpty();
  }
}