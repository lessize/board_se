package com.kh.board.web;

import com.kh.board.domain.entity.Member;
import com.kh.board.domain.member.svc.MemberSVC;
import com.kh.board.web.form.JoinForm;
import com.kh.board.web.form.LoginForm;
import com.kh.board.web.form.LoginMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

  private final MemberSVC memberSVC;

  // 로그인 폼
  @GetMapping("/login")
  public String loginForm() {
    return "/member/login";
  }

  // 로그인 처리
  @PostMapping("/login")
  public String login(LoginForm loginForm,
                      HttpServletRequest request,
                      @RequestParam(value = "redirectUrl", required = false) String redirectUrl) {
    log.info("loginForm={}", loginForm);

    if (memberSVC.existEmail(loginForm.getEmail())) {
      Optional<Member> optionalMember = memberSVC.findByEmailPw(loginForm.getEmail(), loginForm.getPasswd());

      if (optionalMember.isPresent()) {
        HttpSession session = request.getSession(true);
        Member member = optionalMember.get();

        LoginMember loginMember = new LoginMember(member.getMemberId(), member.getEmail(), member.getPasswd(), member.getNickname());
        session.setAttribute("loginMember", loginMember);

        log.info("loginMember={}", loginMember);
      } else {
        return "/login";
      }
    } else {
      return "/login";
    }
    return "/index";
  }

  // 로그아웃
  @ResponseBody
  @PostMapping("/logout")
  public String logout() {
    return "";
  }

  // 회원가입 폼
  @GetMapping("/join")
  public String joinForm() {
    return "/member/joinForm";
  }

  // 회원가입 처리
  @PostMapping("/join")
  public String join(JoinForm joinForm, Model model) {
    // 유효성 체크
    String pattern = "/^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$/i";

    if (!Pattern.matches(pattern, joinForm.getEmail())) {
      model.addAttribute("member", joinForm);
      model.addAttribute("err_msg", "올바른 이메일 주소가 아닙니다.");
      return "redirect:/join";
    }

    // 가입
    Member member = new Member();
    BeanUtils.copyProperties(joinForm, member);
    Long memberId = memberSVC.joinMember(member);

    return memberId != null ? "redirect:/login" : "redirect:/";
  }
}
