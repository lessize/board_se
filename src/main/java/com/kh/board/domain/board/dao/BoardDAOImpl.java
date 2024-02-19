package com.kh.board.domain.board.dao;

import com.kh.board.domain.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
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
import java.util.Optional;

@Slf4j
@Repository
public class BoardDAOImpl implements BoardDAO {

  private NamedParameterJdbcTemplate template;
  BoardDAOImpl(NamedParameterJdbcTemplate template){
    this.template = template;
  }

  // 게시글 등록
  @Override
  public Long save(Board board) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into board(user_id, title, material, writer) ");
    sql.append("values(board_user_id_seq.nextval, :title, :material, :writer) ");

    // SQL 파라미터 자동매핑
    SqlParameterSource param = new BeanPropertySqlParameterSource(board);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(), param, keyHolder, new String[]{"user_id", "title", "material", "writer"});
    Long user_id = ((BigDecimal)keyHolder.getKeys().get("user_id")).longValue();

    return user_id;
  }

  // 게시글 목록
  @Override
  public List<Board> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("select user_id, title, material, writer ");
    sql.append("  from board ");
    sql.append(" order by user_id desc ");


    List<Board> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(Board.class));

    return list;
  }

  // 게시글 조회
  @Override
  public Optional<Board> findById(Long userId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select user_id, title, material, writer, cdate, udate ");
    sql.append("  from board ");
    sql.append(" where user_id = :userId ");

    try {
      Map<String, Long> map = Map.of("userId", userId);
      Board board = template.queryForObject(sql.toString(), map, BeanPropertyRowMapper.newInstance(Board.class));
      return Optional.of(board);
    }catch (EmptyResultDataAccessException e){
      // 조회결과가 없는 경우
      return Optional.empty();
    }
  }

  // 게시글 수정
  @Override
  public int updateById(Long userId, Board board) {
    StringBuffer sql = new StringBuffer();
    sql.append("update board ");
    sql.append("   set title = :title, ");
    sql.append("       material = :material ");
    sql.append(" where user_id = :userId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("userId", userId)
            .addValue("title", board.getTitle())
            .addValue("material", board.getMaterial());

    int updatePost = template.update(sql.toString(), param);

    return updatePost;
  }

  // 단건 삭제
  @Override
  public int deleteById(Long userId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from board ");
    sql.append(" where user_id = :userId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("userId", userId);

    int deletedRowCnt = template.update(sql.toString(), param);

    return deletedRowCnt;
  }

  // 다중 삭제
  @Override
  public int deleteByIds(List<Long> userIds) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from board ");
    sql.append(" where user_id in (:userIds) ");

    Map<String, List<Long>> map = Map.of("userIds", userIds);
    int deletePosts = template.update(sql.toString(), map);
    return deletePosts;
  }
}
