DROP TABLE chat;
DROP SEQUENCE chat_seq;


DROP TABLE chatRoom;
DROP SEQUENCE chatRoom_seq;



CREATE TABLE chatRoom(
cno NUMBER PRIMARY KEY,
id VARCHAR2(20) NOT NULL references member(id) on delete cascade,
title VARCHAR2(300) NOT NULL
);

CREATE SEQUENCE chatRoom_seq;

INSERT INTO chatRoom(cno, id, title)
VALUES(chatRoom_seq.nextval, 'test1', '홍길동 채팅방');
COMMIT;

INSERT INTO chatRoom(cno, id, title)
VALUES(chatRoom_seq.nextval, 'test1', '이순신 채팅방');
COMMIT;

INSERT INTO chatRoom(cno, id, title)
VALUES(chatRoom_seq.nextval, 'test1', '2팀 채팅방');
COMMIT;


select  cr.cno, m.id, cr.title
from chatRoom cr, member m
where (cr.id = m.id)
order by cno desc;



CREATE TABLE chat(
no NUMBER PRIMARY KEY,
id VARCHAR2(20) NOT NULL references member(id) on delete cascade,
content VARCHAR2(2000) NOT NULL,
writeDate DATE DEFAULT SYSDATE,
cno NUMBER NOT NULL references chatRoom(cno) on delete cascade

);

CREATE SEQUENCE chat_seq;

INSERT INTO chat(no, id, content, cno)  
VALUES(chat_seq.nextval, 'test1', '채팅입니다', 1);

INSERT INTO chat(no, id, content, cno)
VALUES(chat_seq.nextval, 'test1', '두번째 채팅입니다', 2);
COMMIT;

INSERT INTO chat(no, id, content, cno)
VALUES(chat_seq.nextval, 'test1', '세번째 채팅입니다', 3);
COMMIT;


select  c.no, c.id, c.content, cr.cno
from chat c, chatRoom cr
where cr.cno = c.cno
order by no desc;

select max(no) FROM chat
where (cno = 8) ; 

