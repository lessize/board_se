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


-- 테이블 삭제
drop table member;

-- 테이블 생성
create table member (
    member_id number(10), -- 내부관리
    email     varchar2(50), -- 로그인 아이디
    passwd    varchar2(12), -- 로그인 비밀번호
    nickname  varchar2(30), -- 별칭
    gubun     varchar2(11) default 'M0101', -- 회원구분 (일반, 우수, 관리자1, 관리자2,,,,)
    pic       blob,        -- 사진
    cdate     timestamp,   -- 생성일 (가입일)
    udate     timestamp    -- 수정일
);


-- 기본키 생성
alter table member add constraint member_member_id_pk primary key (member_id);

-- unique
alter table member modify email constraint member_email_uk unique;

-- default
alter table member modify gubun default 'M0101';
alter table member modify cdate default systimestamp;
alter table member modify udate default systimestamp;

-- 시퀀스
drop sequence member_member_id_seq;
create sequence member_member_id_seq;

-- 샘플데이터
insert into member (member_id, email, passwd, nickname)
    values(member_member_id_seq.nextval, 'user1@kh.com', 'user1', '사용자1');
insert into member (member_id, email, passwd, nickname)
    values(member_member_id_seq.nextval, 'user2@kh.com', 'user2', '사용자2');
insert into member (member_id, email, passwd, nickname)
    values(member_member_id_seq.nextval, 'user3@kh.com', 'user3', '사용자3');
insert into member (member_id, email, passwd, nickname)
    values(member_member_id_seq.nextval, 'user4@kh.com', 'user4', '사용자4');
insert into member (member_id, email, passwd, nickname)
    values(member_member_id_seq.nextval, 'user5@kh.com', 'user5', '사용자5');

commit;