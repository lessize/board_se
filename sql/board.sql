-- 테이블 삭제
drop table board;

-- 시퀀스 삭제
drop sequence board_user_id_seq;

-- 테이블 생성
create table board (
    user_id  number(5),    -- 아이디
    title    varchar2(30), -- 제목
    material clob,         -- 내용
    writer   varchar2(30), -- 작성자
    cdate    timestamp,    -- 작성일자
    udate    timestamp     -- 수정일자
);
--기본키
alter table board add constraint user_id_pk primary key(user_id);

--시퀀스생성
create sequence board_user_id_seq;

-- default
alter table board modify cdate default systimestamp;
alter table board modify udate default systimestamp;

-- not null
alter table board modify title not null;
alter table board modify material not null;
alter table board modify writer not null;

-- 데이터
insert into board (user_id, title, material, writer)
     values (board_user_id_seq.nextval, 'title-1', 'lorem', 'ALICE');
insert into board (user_id, title, material, writer)
     values (board_user_id_seq.nextval, 'title-2', 'lorem', 'BELL');
insert into board (user_id, title, material, writer)
     values (board_user_id_seq.nextval, 'title-3', 'lorem', 'CLARE');
insert into board (user_id, title, material, writer)
     values (board_user_id_seq.nextval, 'title-4', 'lorem', 'DEAN');