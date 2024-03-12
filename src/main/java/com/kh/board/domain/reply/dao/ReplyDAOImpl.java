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
    sql.append("insert into reply (reply_id, user_id, commentary, writer, usermail) ");
    sql.append("values (reply_reply_id_seq.nextval, :userId, :commentary, :writer, :usermail) ");

    // SQL 파라미터 자동매핑
    SqlParameterSource param = new BeanPropertySqlParameterSource(reply);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(), param, keyHolder, new String[]{"reply_id", "user_id", "commentary", "writer", "usermail"});
    Long reply_id = ((BigDecimal)keyHolder.getKeys().get("reply_id")).longValue();

    return reply_id;
  }

  // 목록
  @Override
  public List<Reply> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("select reply_id, user_Id, commentary, writer, cdate, usermail ");
    sql.append("  from reply ");
    sql.append(" order by cdate desc");

    List<Reply> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(Reply.class));

    return list;
  }

  // 수정
  @Override
  public int updateByEmail(Long replyId, Reply reply) {
    StringBuffer sql = new StringBuffer();
    sql.append("update reply ");
    sql.append("   set commentary = :commentary ");
    sql.append(" where reply_id = :replyId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("commentary", reply.getCommentary())
            .addValue("replyId", replyId);

    int updated = template.update(sql.toString(), param);

    return updated;
  }

  // 댓글 삭제
  @Override
  public int deleteById(Long userId, Long replyId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from reply ");
    sql.append(" where user_id = :userId ");
    sql.append("   and reply_id = :reply_id ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("userId", userId)
            .addValue("reply_id", replyId);

    int deletedCnt = template.update(sql.toString(), param);

    return deletedCnt;
  }

  @Override
  public int totalCnt() {
    String sql = "select count(reply_id) from reply ";

    MapSqlParameterSource parameters = new MapSqlParameterSource();
    Integer cnt = template.queryForObject(sql, parameters, Integer.class);

    return cnt;
  }
}
