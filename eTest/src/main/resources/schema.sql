CREATE TABLE ETEST.CURRICULUM_MASTER (
	CURRICULUM_ID VARCHAR(255 CHAR) NOT NULL,
	CHAPTER VARCHAR(255 CHAR),
	CHAPTER_ID VARCHAR(255 CHAR),
	CURRICULUM_SEQUENCE NUMBER(10),
	GRADE VARCHAR(255 CHAR),
	SCHOOL_TYPE VARCHAR(255 CHAR),
	SECTION VARCHAR(255 CHAR),
	SEMESTER VARCHAR(255 CHAR),
	SUB_SECTION VARCHAR(255 CHAR)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.DIAGNOSIS_CURRICULUM (
	CURRICULUM_ID NUMBER NOT NULL,
	CHAPTER VARCHAR(100) NOT NULL,
	SECTION VARCHAR(100),
	SUB_SECTION VARCHAR(100)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.DIAGNOSIS_PROBLEM (
	SUBJECT VARCHAR(100),
	PROB_ID NUMBER NOT NULL,
	CURRICULUM_ID NUMBER NOT NULL,
	SET_TYPE CHAR(1),
	ORDER_NUM NUMBER
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;
COMMENT ON TABLE ETEST.DIAGNOSIS_PROBLEM IS '문제와 이미지 파일 경로의 상관 관계를 저장';
COMMENT ON COLUMN DIAGNOSIS_PROBLEM.SUBJECT IS '진단 지식 문항 주제';
COMMENT ON COLUMN DIAGNOSIS_PROBLEM.PROB_ID IS '문제 ID';
COMMENT ON COLUMN DIAGNOSIS_PROBLEM.CURRICULUM_ID IS '분류 체계 ID (임시로 커리큘럼에)';
COMMENT ON COLUMN DIAGNOSIS_PROBLEM.SET_TYPE IS '타입 (A, B, C)';
COMMENT ON COLUMN DIAGNOSIS_PROBLEM.ORDER_NUM IS '문제 순서';

CREATE TABLE ETEST.DIAGNOSIS_REPORT (
	USER_UUID VARCHAR(36) NOT NULL,
	GI_SCORE NUMBER,
	RISK_SCORE NUMBER,
	INVEST_SCORE NUMBER,
	KNOWLEDGE_SCORE NUMBER,
	AVG_UK_MASTERY NUMBER,
	USER_MBTI VARCHAR(5),
	INVEST_ITEM_NUM NUMBER,
	STOCK_RATIO NUMBER,
	DIAGNOSIS_DATE TIMESTAMP(6) NOT NULL
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.ERROR_REPORT (
	ERROR_ID NUMBER NOT NULL,
	PROB_ID NUMBER NOT NULL,
	USER_UUID VARCHAR(36) NOT NULL,
	REPORT_TYPE VARCHAR(32) NOT NULL,
	REPORT_TEXT VARCHAR(2000)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;



CREATE TABLE ETEST.MINITEST_REPORT (
	USER_UUID VARCHAR(36) NOT NULL,
	AVG_UK_MASTERY NUMBER NOT NULL,
	SET_NUM NUMBER NOT NULL,
	CORRECT_NUM NUMBER,
	WRONG_NUM NUMBER,
	DUNNO_NUM NUMBER,
	MINITEST_DATE TIMESTAMP(6) NOT NULL
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.PROBLEM (
	PROB_ID NUMBER NOT NULL,
	ANSWER_TYPE VARCHAR(50) NOT NULL,
	QUESTION VARCHAR(40000) NOT NULL,
	SOLUTION VARCHAR(40000) NOT NULL,
	DIFFICULTY VARCHAR(20),
	CATEGORY VARCHAR(20),
	PART VARCHAR(100),
	IMG_SRC VARCHAR(256),
	TIME_RECOMMENDATION NUMBER,
	CREATOR_ID VARCHAR(32),
	CREATE_DATE TIMESTAMP(6),
	VALIDATOR_ID VARCHAR(32),
	VALIDATE_DATE TIMESTAMP(6),
	EDITOR_ID VARCHAR(32),
	EDIT_DATE TIMESTAMP(6),
	SOURCE VARCHAR(512),
	INTENTION VARCHAR(100)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;
COMMENT ON TABLE ETEST.PROBLEM IS '문제 내용을 저장';
COMMENT ON COLUMN PROBLEM.PROB_ID IS '문제 ID';
COMMENT ON COLUMN PROBLEM.ANSWER_TYPE IS '답안 유형';
COMMENT ON COLUMN PROBLEM.QUESTION IS '질문';
COMMENT ON COLUMN PROBLEM.SOLUTION IS '해설';
COMMENT ON COLUMN PROBLEM.DIFFICULTY IS '문제 난이도';
COMMENT ON COLUMN PROBLEM.CATEGORY IS '분류';
COMMENT ON COLUMN PROBLEM.PART IS '파트';
COMMENT ON COLUMN PROBLEM.IMG_SRC IS '이미지 경로';
COMMENT ON COLUMN PROBLEM.TIME_RECOMMENDATION IS '적정 풀이 시간';
COMMENT ON COLUMN PROBLEM.CREATOR_ID IS '생성자';
COMMENT ON COLUMN PROBLEM.CREATE_DATE IS '생성 날짜';
COMMENT ON COLUMN PROBLEM.VALIDATOR_ID IS '검수자';
COMMENT ON COLUMN PROBLEM.VALIDATE_DATE IS '검수 날짜';
COMMENT ON COLUMN PROBLEM.EDITOR_ID IS '수정자';
COMMENT ON COLUMN PROBLEM.EDIT_DATE IS '수정 날짜';
COMMENT ON COLUMN PROBLEM.SOURCE IS '문제 출처';
COMMENT ON COLUMN PROBLEM.INTENTION IS '출제 의도';

CREATE TABLE ETEST.PROBLEM_CHOICE (
	PROB_ID NUMBER NOT NULL,
	CHOICE_NUM NUMBER NOT NULL,
	TEXT VARCHAR(512) NOT NULL,
	UK_ID NUMBER,
	CHOICE_SCORE NUMBER
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;
COMMENT ON TABLE ETEST.PROBLEM_CHOICE IS '문제와 이미지 파일 경로의 상관 관계를 저장';
COMMENT ON COLUMN PROBLEM_CHOICE.PROB_ID IS '문제 ID';
COMMENT ON COLUMN PROBLEM_CHOICE.CHOICE_NUM IS '문제 보기 번호';
COMMENT ON COLUMN PROBLEM_CHOICE.TEXT IS '문제 보기 텍스트';
COMMENT ON COLUMN PROBLEM_CHOICE.UK_ID IS 'UK_ID';

CREATE TABLE ETEST.PROBLEM_UK_REL (
	PROB_ID NUMBER NOT NULL,
	UK_ID NUMBER NOT NULL
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;
COMMENT ON TABLE ETEST.PROBLEM_UK_REL IS '문제와 지식 추적 UK의 상관 관계를 저장';
COMMENT ON COLUMN PROBLEM_UK_REL.PROB_ID IS '문제 ID';
COMMENT ON COLUMN PROBLEM_UK_REL.UK_ID IS 'UK_ID';

CREATE TABLE ETEST.TEST_PROBLEM (
	SET_NUM NUMBER NOT NULL,
	SEQUENCE NUMBER NOT NULL,
	PROB_ID NUMBER NOT NULL,
	SUBJECT NUMBER(10)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;
COMMENT ON TABLE ETEST.TEST_PROBLEM IS '문제와 이미지 파일 경로의 상관 관계를 저장';
COMMENT ON COLUMN TEST_PROBLEM.SET_NUM IS '모의테스트 세트 번호';
COMMENT ON COLUMN TEST_PROBLEM.SEQUENCE IS '모의 테스트 세트 내 순서';
COMMENT ON COLUMN TEST_PROBLEM.PROB_ID IS '문제 ID';

CREATE TABLE ETEST.UK_MASTER (
	UK_ID NUMBER NOT NULL,
	UK_NAME VARCHAR(200) NOT NULL,
	UK_DESCRIPTION VARCHAR(1000),
	TRAIN_UNSEEN CHAR(1) NOT NULL,
	CURRICULUM_ID VARCHAR(255 CHAR),
	PART VARCHAR(50)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.UK_REL (
	BASE_UK_ID NUMBER NOT NULL,
	PRE_UK_ID NUMBER NOT NULL,
	RELATION_REFERENCE VARCHAR(32)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.USER_EMBEDDING (
	USER_UUID VARCHAR(255 CHAR) NOT NULL,
	UPDATE_DATE TIMESTAMP(6),
	USER_EMBEDDING VARCHAR(255 CHAR)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.USER_KNOWLEDGE (
	USER_UUID VARCHAR(36) NOT NULL,
	UK_MASTERY NUMBER,
	UPDATE_DATE TIMESTAMP(6),
	UK_ID VARCHAR(255 CHAR) NOT NULL
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.USER_MASTER (
	USER_UUID VARCHAR(36) NOT NULL,
	PROVIDER_ID VARCHAR(1000) NOT NULL,
	ROLE VARCHAR(100),
    PASSWORD VARCHAR(1000),
    EMAIL VARCHAR(100) NOT NULL,
    GENDER VARCHAR(100),
    PROVIDER VARCHAR(100),
    USER_TYPE VARCHAR(100),
	BIRTHDAY TIMESTAMP(6),
    COLLECT_INFO VARCHAR(10),
    SERVICE_AGREEMENT VARCHAR(10),
    OLDER_THAN_14 VARCHAR(10),
    ACCOUNT_ACTIVE VARCHAR(10),
    EVENT_SMS_AGREEMENT VARCHAR(10)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;


CREATE TABLE ETEST.CS_INQUIRY (
	USER_UUID VARCHAR(36) NOT NULL,
	INQUIRY_ID NUMBER NOT NULL,
	INQUIRY_STATUS VARCHAR(100) NOT NULL,
	INQUIRY_TITLE VARCHAR(100),
    INQUIRY_TYPE VARCHAR(100),
    INQUIRY_DATE TIMESTAMP(6),
    INQUIRY_CONTENT VARCHAR(100),
    INQUIRY_ANSWER VARCHAR(2048)


)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.CS_FAQ (
	FAQ_ID NUMBER NOT NULL,
	FAQ_TITLE VARCHAR(100),
	FAQ_CONTENT VARCHAR(100)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.CS_NOTICE (
	NOTICE_ID NUMBER NOT NULL,
	NOTICE_TITLE VARCHAR(100),
	NOTICE_DATE TIMESTAMP(6),
    NOTICE_CONTENT VARCHAR(2048)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;



CREATE SEQUENCE NOTICE_SEQUENCE INCREMENT BY 1 START WITH 1;

CREATE SEQUENCE FAQ_SEQUENCE INCREMENT BY 1 START WITH 1;

CREATE SEQUENCE INQUIRY_SEQUENCE INCREMENT BY 1 START WITH 1;

CREATE TABLE ETEST.CS_INQUIRY_FILE (
	INQUIRY_FILE_ID NUMBER NOT NULL,
	INQUIRY_FILE_URL VARCHAR(100) NOT NULL,
	INQUIRY_FILE_TYPE VARCHAR(100),
    INQUIRY_ID NUMBER
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE UNIQUE INDEX ETEST.ETEST_CON1282100128 ON ETEST.CURRICULUM_MASTER (
	CURRICULUM_ID ASC
)
LOGGING
TABLESPACE USR
PCTFREE 10
INITRANS 2;

CREATE UNIQUE INDEX ETEST.ETEST_CON1287100143 ON ETEST.DIAGNOSIS_CURRICULUM (
	CURRICULUM_ID ASC
)
LOGGING
TABLESPACE USR
PCTFREE 10
INITRANS 2;

CREATE UNIQUE INDEX ETEST.PK_DIAGNOSIS_PROBLEM ON ETEST.DIAGNOSIS_PROBLEM (
	PROB_ID ASC
)
LOGGING
TABLESPACE USR
PCTFREE 10
INITRANS 2;

CREATE UNIQUE INDEX ETEST.PK_DIAGNOSIS_REPORT ON ETEST.DIAGNOSIS_REPORT (
	USER_UUID ASC,
	DIAGNOSIS_DATE ASC
)
LOGGING
TABLESPACE USR
PCTFREE 10
INITRANS 2;

CREATE UNIQUE INDEX ETEST.PK_ERROR_REPORT ON ETEST.ERROR_REPORT (
	ERROR_ID ASC
)
LOGGING
TABLESPACE USR
PCTFREE 10
INITRANS 2;

CREATE UNIQUE INDEX ETEST.ETEST_CON1286500084 ON ETEST.MINITEST_REPORT (
	USER_UUID ASC,
	MINITEST_DATE ASC
)
LOGGING
TABLESPACE USR
PCTFREE 10
INITRANS 2;

CREATE UNIQUE INDEX ETEST.PK_PROBLEM ON ETEST.PROBLEM (
	PROB_ID ASC
)
LOGGING
TABLESPACE USR
PCTFREE 10
INITRANS 2;

CREATE UNIQUE INDEX ETEST.PK_UK_MASTER ON ETEST.UK_MASTER (
	UK_ID ASC
)
LOGGING
TABLESPACE USR
PCTFREE 10
INITRANS 2;

CREATE UNIQUE INDEX ETEST.ETEST_CON1290000468 ON ETEST.USER_EMBEDDING (
	USER_UUID ASC
)
LOGGING
TABLESPACE USR
PCTFREE 10
INITRANS 2;

CREATE UNIQUE INDEX ETEST.PK_USER_KNOWLEDGE ON ETEST.USER_KNOWLEDGE (
	USER_UUID ASC,
	UK_ID ASC
)
LOGGING
TABLESPACE USR
PCTFREE 10
INITRANS 2;

CREATE UNIQUE INDEX ETEST.PK_USER_MASTER ON ETEST.USER_MASTER (
	USER_UUID ASC
)
LOGGING
TABLESPACE USR
PCTFREE 10
INITRANS 2;

ALTER TABLE CURRICULUM_MASTER ADD CONSTRAINT ETEST_CON1282100128
PRIMARY KEY (
	CURRICULUM_ID
);

ALTER TABLE DIAGNOSIS_CURRICULUM ADD CONSTRAINT ETEST_CON1287100143
PRIMARY KEY (
	CURRICULUM_ID
);

ALTER TABLE DIAGNOSIS_PROBLEM ADD CONSTRAINT PK_DIAGNOSIS_PROBLEM
PRIMARY KEY (
	PROB_ID
);

ALTER TABLE DIAGNOSIS_REPORT ADD CONSTRAINT PK_DIAGNOSIS_REPORT
PRIMARY KEY (
	USER_UUID,
	DIAGNOSIS_DATE
);

ALTER TABLE ERROR_REPORT ADD CONSTRAINT PK_ERROR_REPORT
PRIMARY KEY (
	ERROR_ID
);

ALTER TABLE MINITEST_REPORT ADD CONSTRAINT ETEST_CON1286500084
PRIMARY KEY (
	USER_UUID,
	MINITEST_DATE
);

ALTER TABLE PROBLEM ADD CONSTRAINT PK_PROBLEM
PRIMARY KEY (
	PROB_ID
);

ALTER TABLE UK_MASTER ADD CONSTRAINT PK_UK_MASTER
PRIMARY KEY (
	UK_ID
);

ALTER TABLE USER_EMBEDDING ADD CONSTRAINT ETEST_CON1290000468
PRIMARY KEY (
	USER_UUID
);

ALTER TABLE USER_KNOWLEDGE ADD CONSTRAINT PK_USER_KNOWLEDGE
PRIMARY KEY (
	USER_UUID,
	UK_ID
);

ALTER TABLE USER_MASTER ADD CONSTRAINT PK_USER_MASTER
PRIMARY KEY (
	USER_UUID
);

ALTER TABLE CS_NOTICE ADD CONSTRAINT PK_NOTICE_ID
PRIMARY KEY (
	NOTICE_ID
);

ALTER TABLE CS_FAQ ADD CONSTRAINT PK_FAQ_ID
PRIMARY KEY (
	FAQ_ID
);

ALTER TABLE CS_INQUIRY ADD CONSTRAINT PK_INQUIRY_ID
PRIMARY KEY (
	INQUIRY_ID
);

CREATE SEQUENCE ERROR_SEQ INCREMENT BY 1 START WITH 1;

CREATE TABLE ETEST.ARTICLE
(
    ARTICLE_ID     NUMBER             NOT NULL, 
    ARTICLE_URL    VARCHAR2(32)       NOT NULL, 
    TITLE          VARCHAR2(128)      NOT NULL, 
    CREATE_DATE    TIMESTAMP          NOT NULL, 
    CREATOR_ID     VARCHAR2(32)       NOT NULL, 
    DESCRIPTION    VARCHAR2(10000)    NOT NULL, 
    IMG_SRC        VARCHAR2(32)       NOT NULL, 
    SOURCE         VARCHAR2(128)      NULL, 
    CONSTRAINT PK_ARTICLE PRIMARY KEY (ARTICLE_ID)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
  MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.WIKI
(
    WIKI_ID        NUMBER             NOT NULL, 
    TITLE          VARCHAR2(128)      NOT NULL, 
    CREATE_DATE    TIMESTAMP          NOT NULL, 
    CREATOR_ID     VARCHAR2(32)       NOT NULL, 
    DESCRIPTION    VARCHAR2(10000)    NOT NULL, 
    SUMMARY        VARCHAR2(256)      NOT NULL, 
    SOURCE         VARCHAR2(128)      NULL, 
    CONSTRAINT PK_WIKI PRIMARY KEY (WIKI_ID)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
  MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.VIDEO_CURRICULUM
(
    CURRICULUM_ID    NUMBER           NOT NULL, 
    SUBJECT          VARCHAR2(128)    NOT NULL, 
    CONSTRAINT PK_VIDEO_CURRICULUM PRIMARY KEY (CURRICULUM_ID)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
  MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.VIDEO
(
    VIDEO_ID             NUMBER           NOT NULL, 
    VIDEO_SRC            VARCHAR2(32)     NOT NULL, 
    ORIGINAL_FILE_SRC    VARCHAR2(32)     NOT NULL, 
    TITLE                VARCHAR2(128)    NOT NULL, 
    CREATE_DATE          TIMESTAMP        NOT NULL, 
    CREATOR_ID           VARCHAR2(32)     NOT NULL, 
    IMG_SRC              VARCHAR2(32)     NOT NULL, 
    TOTAL_TIME           FLOAT            NOT NULL, 
    CURRICULUM_ID        NUMBER           NOT NULL, 
    CONSTRAINT PK_VIDEO PRIMARY KEY (VIDEO_ID)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
  MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

ALTER TABLE ETEST.VIDEO
    ADD CONSTRAINT FK_VIDEO_CURRICULUM_ID_VIDEO_C FOREIGN KEY (CURRICULUM_ID)
        REFERENCES ETEST.VIDEO_CURRICULUM (CURRICULUM_ID)


CREATE TABLE ETEST.BOOK
(
    BOOK_ID        NUMBER            NOT NULL, 
    BOOK_SRC       VARCHAR2(32)      NOT NULL, 
    TITLE          VARCHAR2(128)     NOT NULL, 
    CREATE_DATE    TIMESTAMP         NOT NULL, 
    CREATOR_ID     VARCHAR2(32)      NOT NULL, 
    IMG_SRC        VARCHAR2(32)      NOT NULL, 
    DESCRIPTION    VARCHAR2(1024)    NOT NULL, 
    CONSTRAINT PK_BOOK PRIMARY KEY (BOOK_ID)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
  MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.VIDEO_HIT
(
    VIDEO_ID    NUMBER    NOT NULL, 
    HIT         NUMBER    NULL, 
    CONSTRAINT PK_VIDEO_HIT PRIMARY KEY (VIDEO_ID)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
  MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

ALTER TABLE ETEST.VIDEO_HIT
    ADD CONSTRAINT FK_VIDEO_HIT_VIDEO_ID_VIDEO_VI FOREIGN KEY (VIDEO_ID)
        REFERENCES ETEST.VIDEO (VIDEO_ID);

CREATE TABLE ETEST.ARTICLE_BOOKMARK
(
    USER_UUID     VARCHAR2(36)    NOT NULL, 
    ARTICLE_ID    NUMBER          NOT NULL, 
    CONSTRAINT PK_ARTICLE_BOOKMARK PRIMARY KEY (USER_UUID, ARTICLE_ID)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
  MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

ALTER TABLE ETEST.ARTICLE_BOOKMARK
    ADD CONSTRAINT FK_ARTICLE_BOOKMARK_ARTICLE_ID FOREIGN KEY (ARTICLE_ID)
        REFERENCES ETEST.ARTICLE (ARTICLE_ID);

ALTER TABLE ETEST.ARTICLE_BOOKMARK
    ADD CONSTRAINT FK_ARTICLE_BOOKMARK_USER_UUID_ FOREIGN KEY (USER_UUID)
        REFERENCES ETEST.USER_MASTER (USER_UUID);


CREATE TABLE ETEST.WIKI_BOOKMARK
(
    USER_UUID    VARCHAR2(36)    NOT NULL, 
    WIKI_ID      NUMBER          NOT NULL, 
    CONSTRAINT PK_WIKI_BOOKMARK PRIMARY KEY (USER_UUID, WIKI_ID)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
  MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

ALTER TABLE ETEST.WIKI_BOOKMARK
    ADD CONSTRAINT FK_WIKI_BOOKMARK_WIKI_ID_WIKI_ FOREIGN KEY (WIKI_ID)
        REFERENCES ETEST.WIKI (WIKI_ID);

ALTER TABLE ETEST.WIKI_BOOKMARK
    ADD CONSTRAINT FK_WIKI_BOOKMARK_USER_UUID_USE FOREIGN KEY (USER_UUID)
        REFERENCES ETEST.USER_MASTER (USER_UUID);


CREATE TABLE ETEST.BOOK_BOOKMARK
(
    USER_UUID    VARCHAR2(36)    NOT NULL, 
    BOOK_ID      NUMBER          NOT NULL, 
    CONSTRAINT PK_BOOK_BOOKMARK PRIMARY KEY (USER_UUID, BOOK_ID)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
  MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

ALTER TABLE ETEST.BOOK_BOOKMARK
    ADD CONSTRAINT FK_BOOK_BOOKMARK_BOOK_ID_BOOK_ FOREIGN KEY (BOOK_ID)
        REFERENCES ETEST.BOOK (BOOK_ID);

ALTER TABLE ETEST.BOOK_BOOKMARK
    ADD CONSTRAINT FK_BOOK_BOOKMARK_USER_UUID_USE FOREIGN KEY (USER_UUID)
        REFERENCES ETEST.USER_MASTER (USER_UUID);


CREATE TABLE ETEST.ARTICLE_UK_REL
(
    ARTICLE_ID    NUMBER    NOT NULL, 
    UK_ID         NUMBER    NOT NULL, 
    CONSTRAINT PK_ARTICLE_UK_REL PRIMARY KEY (ARTICLE_ID, UK_ID)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
  MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

ALTER TABLE ETEST.ARTICLE_UK_REL
    ADD CONSTRAINT FK_ARTICLE_UK_REL_ARTICLE_ID_A FOREIGN KEY (ARTICLE_ID)
        REFERENCES ETEST.ARTICLE (ARTICLE_ID);

ALTER TABLE ETEST.ARTICLE_UK_REL
    ADD CONSTRAINT FK_ARTICLE_UK_REL_UK_ID_UK_MAS FOREIGN KEY (UK_ID)
        REFERENCES ETEST.UK_MASTER (UK_ID);


CREATE TABLE ETEST.WIKI_UK_REL
(
    WIKI_ID    NUMBER    NOT NULL, 
    UK_ID      NUMBER    NOT NULL, 
    CONSTRAINT PK_WIKI_UK_REL PRIMARY KEY (WIKI_ID, UK_ID)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
  MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

ALTER TABLE ETEST.WIKI_UK_REL
    ADD CONSTRAINT FK_WIKI_UK_REL_WIKI_ID_WIKI_WI FOREIGN KEY (WIKI_ID)
        REFERENCES ETEST.WIKI (WIKI_ID);

ALTER TABLE ETEST.WIKI_UK_REL
    ADD CONSTRAINT FK_WIKI_UK_REL_UK_ID_UK_MASTER FOREIGN KEY (UK_ID)
        REFERENCES ETEST.UK_MASTER (UK_ID);


CREATE TABLE ETEST.VIDEO_UK_REL
(
    VIDEO_ID    NUMBER    NOT NULL, 
    UK_ID       NUMBER    NOT NULL, 
    CONSTRAINT PK_VIDEO_UK_REL PRIMARY KEY (VIDEO_ID, UK_ID)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
  MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

ALTER TABLE ETEST.VIDEO_UK_REL
    ADD CONSTRAINT FK_VIDEO_UK_REL_VIDEO_ID_VIDEO FOREIGN KEY (VIDEO_ID)
        REFERENCES ETEST.VIDEO (VIDEO_ID);

ALTER TABLE ETEST.VIDEO_UK_REL
    ADD CONSTRAINT FK_VIDEO_UK_REL_UK_ID_UK_MASTE FOREIGN KEY (UK_ID)
        REFERENCES ETEST.UK_MASTER (UK_ID);


CREATE TABLE ETEST.VIDEO_BOOKMARK
(
    USER_UUID    VARCHAR2(36)    NOT NULL, 
    VIDEO_ID     NUMBER          NOT NULL, 
    CONSTRAINT PK_VIDEO_BOOKMARK PRIMARY KEY (USER_UUID, VIDEO_ID)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
  MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

ALTER TABLE ETEST.VIDEO_BOOKMARK
    ADD CONSTRAINT FK_VIDEO_BOOKMARK_VIDEO_ID_VID FOREIGN KEY (VIDEO_ID)
        REFERENCES ETEST.VIDEO (VIDEO_ID);

ALTER TABLE ETEST.VIDEO_BOOKMARK
    ADD CONSTRAINT FK_VIDEO_BOOKMARK_USER_UUID_US FOREIGN KEY (USER_UUID)
        REFERENCES ETEST.USER_MASTER (USER_UUID);
        
CREATE TABLE ETEST.STATEMENT
(
    STATEMENT_ID    VARCHAR2(100)     NOT NULL, 
    USER_ID         VARCHAR2(100)     NOT NULL, 
    ACTION_TYPE     VARCHAR2(100)     NOT NULL, 
    SOURCE_TYPE     VARCHAR2(100)     NOT NULL, 
    SOURCE_ID       VARCHAR2(100)     NULL, 
    TIMESTAMP       VARCHAR2(100)     NOT NULL, 
    PLATFORM        VARCHAR2(100)     NULL, 
    CURSOR_TIME     NUMBER            NULL, 
    IS_CORRECT      NUMBER            NULL, 
    IS_DELETED      NUMBER            NULL, 
    USER_ANSWER     VARCHAR2(500)     NULL, 
    DURATION        VARCHAR2(100)     NULL, 
    EXTENSION       VARCHAR2(2000)    NULL, 
    CONSTRAINT PK_STATEMENT_MASTER PRIMARY KEY (STATEMENT_ID)
);

CREATE TABLE ETEST.HASHTAG
(
    HASHTAG_ID    NUMBER           NOT NULL, 
    NAME          VARCHAR2(128)    NULL, 
     PRIMARY KEY (HASHTAG_ID)
)
LOGGING
TABLESPACE USR
PCTFREE 10
INITRANS 2;

CREATE TABLE ETEST.VIDEO_HASHTAG
(
    VIDEO_ID      NUMBER    NOT NULL, 
    HASHTAG_ID    NUMBER    NOT NULL, 
     PRIMARY KEY (VIDEO_ID, HASHTAG_ID)
)
LOGGING
TABLESPACE USR
PCTFREE 10
INITRANS 2;

ALTER TABLE ETEST.VIDEO_HASHTAG
    ADD CONSTRAINT FK_VIDEO_HASHTAG_VIDEO_ID_VIDE FOREIGN KEY (VIDEO_ID)
        REFERENCES ETEST.VIDEO (VIDEO_ID);

ALTER TABLE ETEST.VIDEO_HASHTAG
    ADD CONSTRAINT FK_VIDEO_HASHTAG_HASHTAG_ID_HA FOREIGN KEY (HASHTAG_ID)
        REFERENCES ETEST.HASHTAG (HASHTAG_ID);

CREATE TABLE ETEST.HIT_STAT
(
    STAT_DATE      DATE      NOT NULL, 
    HIT_STAT_ID    NUMBER    NOT NULL, 
    VIDEO_HIT      NUMBER    NULL, 
    BOOK_HIT       NUMBER    NULL, 
    WIKI_HIT       NUMBER    NULL, 
    ARTICLE_HIT    NUMBER    NULL, 
     PRIMARY KEY (STAT_DATE, HIT_STAT_ID)
)
LOGGING
TABLESPACE USR
PCTFREE 10
INITRANS 2;

CREATE SEQUENCE HIT_STAT_SEQ INCREMENT BY 1 START WITH 1;

------- CREATE TABLE FOR BATCH JOB -------
CREATE TABLE ETEST.BATCH_JOB_INSTANCE  (
	JOB_INSTANCE_ID NUMBER(19,0)  NOT NULL PRIMARY KEY ,
	VERSION NUMBER(19,0) ,
	JOB_NAME VARCHAR2(100 char) NOT NULL,
	JOB_KEY VARCHAR2(32 char) NOT NULL,
	constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ;

CREATE TABLE ETEST.BATCH_JOB_EXECUTION  (
	JOB_EXECUTION_ID NUMBER(19,0)  NOT NULL PRIMARY KEY ,
	VERSION NUMBER(19,0)  ,
	JOB_INSTANCE_ID NUMBER(19,0) NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	START_TIME TIMESTAMP DEFAULT NULL ,
	END_TIME TIMESTAMP DEFAULT NULL ,
	STATUS VARCHAR2(10 char) ,
	EXIT_CODE VARCHAR2(2500 char) ,
	EXIT_MESSAGE VARCHAR2(2500 char) ,
	LAST_UPDATED TIMESTAMP,
	JOB_CONFIGURATION_LOCATION VARCHAR(2500 char) NULL,
	constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
	references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ;

CREATE TABLE ETEST.BATCH_JOB_EXECUTION_PARAMS  (
	JOB_EXECUTION_ID NUMBER(19,0) NOT NULL ,
	TYPE_CD VARCHAR2(6 char) NOT NULL ,
	KEY_NAME VARCHAR2(100 char) NOT NULL ,
	STRING_VAL VARCHAR2(250 char) ,
	DATE_VAL TIMESTAMP DEFAULT NULL ,
	LONG_VAL NUMBER(19,0) ,
	DOUBLE_VAL NUMBER ,
	IDENTIFYING CHAR(1) NOT NULL ,
	constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE ETEST.BATCH_STEP_EXECUTION  (
	STEP_EXECUTION_ID NUMBER(19,0)  NOT NULL PRIMARY KEY ,
	VERSION NUMBER(19,0) NOT NULL,
	STEP_NAME VARCHAR2(100 char) NOT NULL,
	JOB_EXECUTION_ID NUMBER(19,0) NOT NULL,
	START_TIME TIMESTAMP NOT NULL ,
	END_TIME TIMESTAMP DEFAULT NULL ,
	STATUS VARCHAR2(10 char) ,
	COMMIT_COUNT NUMBER(19,0) ,
	READ_COUNT NUMBER(19,0) ,
	FILTER_COUNT NUMBER(19,0) ,
	WRITE_COUNT NUMBER(19,0) ,
	READ_SKIP_COUNT NUMBER(19,0) ,
	WRITE_SKIP_COUNT NUMBER(19,0) ,
	PROCESS_SKIP_COUNT NUMBER(19,0) ,
	ROLLBACK_COUNT NUMBER(19,0) ,
	EXIT_CODE VARCHAR2(2500 char) ,
	EXIT_MESSAGE VARCHAR2(2500 char) ,
	LAST_UPDATED TIMESTAMP,
	constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE ETEST.BATCH_STEP_EXECUTION_CONTEXT  (
	STEP_EXECUTION_ID NUMBER(19,0) NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR2(2500 char) NOT NULL,
	SERIALIZED_CONTEXT CLOB ,
	constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
	references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ;

CREATE TABLE ETEST.BATCH_JOB_EXECUTION_CONTEXT  (
	JOB_EXECUTION_ID NUMBER(19,0) NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR2(2500 char) NOT NULL,
	SERIALIZED_CONTEXT CLOB ,
	constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE SEQUENCE ETEST.BATCH_STEP_EXECUTION_SEQ START WITH 0 MINVALUE 0 MAXVALUE 9223372036854775807 NOCYCLE;
CREATE SEQUENCE ETEST.BATCH_JOB_EXECUTION_SEQ START WITH 0 MINVALUE 0 MAXVALUE 9223372036854775807 NOCYCLE;
CREATE SEQUENCE ETEST.BATCH_JOB_SEQ START WITH 0 MINVALUE 0 MAXVALUE 9223372036854775807 NOCYCLE;
