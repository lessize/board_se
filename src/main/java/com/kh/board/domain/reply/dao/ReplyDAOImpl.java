package com.kh.board.domain.reply.dao;

import com.kh.board.domain.entity.Reply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

  // 목록
  @Override
  public List<Reply> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("select reply_id, user_Id, commentary, writer, cdate ");
    sql.append("  from reply ");
    sql.append(" order by cdate asc");

    List<Reply> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(Reply.class));

    return list;
  }

//  // 목록
//  @Override
//  public List<Reply> findAll(Long userId, Long reqPage, Long recCnt) {
//    StringBuffer sql = new StringBuffer();
//    sql.append("select reply_id, user_Id, commentary, writer, cdate, udate ");
//    sql.append("  from reply ");
//    sql.append(" where user_Id = :userId ");
//    sql.append(" order by cdate asc");
//    sql.append("offset (:reqPage - 1) * :recCnt rows fetch first :recCnt rows only ");
//
//    Map<String, Long> param = Map.of("userId", userId, "reqPage", reqPage, "recCnt", recCnt);
//    List<Reply> list = template.query(sql.toString(), param, BeanPropertyRowMapper.newInstance(Reply.class));
//
//    return list;
//  }

  @Override
  public int totalCnt() {
    String sql = "select count(reply_id) from reply ";

    MapSqlParameterSource parameters = new MapSqlParameterSource();
    Integer cnt = template.queryForObject(sql, parameters, Integer.class);

    return cnt;
  }
}
