package com.kh.board.domain.reply.dao;

import com.kh.board.domain.entity.Reply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Slf4j
@Repository
public class ReplyDAOImpl implements ReplyDAO {

  private NamedParameterJdbcTemplate template;

  ReplyDAOImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  // 등록
  @Override
  public Long rpSave(Reply reply) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into reply (reply_id, user_id, commentary, writer) ");
    sql.append("values (reply_reply_id_seq.nextval, :userId, :commentary, :writer) ");

    // SQL 파라미터 자동매핑
    SqlParameterSource param = new BeanPropertySqlParameterSource(reply);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(), param, keyHolder, new String[]{"reply_id", "user_id", "commentary", "writer"});
    Long reply_id = ((BigDecimal)keyHolder.getKeys().get("reply_id")).longValue();

    return reply_id;
  }
}
