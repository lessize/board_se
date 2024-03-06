package com.kh.board.domain.member.dao;

import com.kh.board.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberDAOImpl implements MemberDAO {

  private final NamedParameterJdbcTemplate template;

  // 회원 가입
  @Override
  public Long insertMember(Member member) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into member (member_id, email, passwd, nickname) ");
    sql.append("values (member_member_id_seq.nextval, :email, :passwd, :nickname) ");


    SqlParameterSource param = new BeanPropertySqlParameterSource(member);
    KeyHolder keyHolder = new GeneratedKeyHolder();

    template.update(sql.toString(), param, keyHolder, new String[]{"member_id"});
    Long member_id = ((BigDecimal)keyHolder.getKeys().get("member_id")).longValue();

    return member_id;
  }

  // 이메일 유무
  @Override
  public boolean existEmail(String email) {
    String sql = "select count(email) from member where email = :email ";

    Map param = Map.of("email", email);
    Integer cnt = template.queryForObject(sql, param, Integer.class);

    return cnt == 1 ? true : false;
  }

  // 회원 조회
  @Override
  public Optional<Member> findByEmailPw(String email, String passwd) {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from member ");
    sql.append(" where email = :email ");
    sql.append("   and passwd = :passwd ");

    Map param = Map.of("email", email, "passwd", passwd);
    try {
      Member member = template.queryForObject(sql.toString(), param, new BeanPropertyRowMapper<>(Member.class));
      return Optional.of(member);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }
}
