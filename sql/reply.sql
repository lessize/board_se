-- 테이블 삭제
drop table reply;

-- 테이블 생성
create table reply (
    reply_id    number(5), -- 아이디
    user_id     number(5), -- 원글번호
    commentary  clob,      -- 내용
    writer      varchar2(30),  -- 작성자
    cdate       timestamp, -- 작성일자
    udate       timestamp -- 수정일자
);

-- 기본키
alter table reply add constraint reply_id_pk primary key (reply_id);

-- 외래키
alter table reply add constraint user_id_fk foreign key (user_id) references board;

-- 시퀀스 삭제
drop sequence reply_reply_id_seq;

-- 시퀀스 생성
create sequence reply_reply_id_seq;

-- default
alter table reply modify cdate default systimestamp;
alter table reply modify udate default systimestamp;

-- not null
alter table reply modify user_id not null;
alter table reply modify commentary not null;
alter table reply modify writer not null;

-- 데이터
insert into reply (reply_id, user_id, commentary, writer) values (reply_reply_id_seq.nextval, 4, 'lorem1', 'AUSTINE');
insert into reply (reply_id, user_id, commentary, writer) values (reply_reply_id_seq.nextval, 4, 'lorem2', 'BETTY');
insert into reply (reply_id, user_id, commentary, writer) values (reply_reply_id_seq.nextval, 4, 'lorem3', 'CHRIS');
insert into reply (reply_id, user_id, commentary, writer) values (reply_reply_id_seq.nextval, 4, 'lorem4', 'DAISY');
insert into reply (reply_id, user_id, commentary, writer) values (reply_reply_id_seq.nextval, 6, 'lorem5', 'EVA');
insert into reply (reply_id, user_id, commentary, writer) values (reply_reply_id_seq.nextval, 6, 'lorem6', 'FELIX');

alter table reply add email varchar2(50);

commit;