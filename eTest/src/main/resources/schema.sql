CREATE SEQUENCE ETEST.PART_SEQUENCE INCREMENT BY 1 START WITH 7;

CREATE TABLE ETEST.PART (
	PART_ID NUMBER NOT NULL,
	PART_NAME VARCHAR(100) NOT NULL,
	ORDER_NUM NUMBER NOT NULL,
	PROBLEM_COUNT NUMBER NOT NULL
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
	SECTION VARCHAR(100) NOT NULL,
	SUB_SECTION VARCHAR(100) NOT NULL,
	SUBJECT VARCHAR(100),
	SET_TYPE CHAR(1),
	STATUS VARCHAR(20) NOT NULL
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
	PROB_ID NUMBER NOT NULL,
	CURRICULUM_ID NUMBER NOT NULL,
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

CREATE TABLE ETEST.DIAGNOSIS_REPORT
(
    DIAGNOSIS_ID               VARCHAR2(36)     NOT NULL, 
    USER_UUID                  VARCHAR2(36)     NOT NULL, 
    GI_SCORE                   FLOAT            NULL, 
    RISK_SCORE                 NUMBER           NULL, 
    RISK_PROFILE_SCORE         NUMBER           NULL, 
    RISK_TRACING_SCORE         NUMBER           NULL, 
    RISK_LEVEL_SCORE           NUMBER           NULL, 
    RISK_CAPA_SCORE            NUMBER           NULL, 
    INVEST_SCORE               NUMBER           NULL, 
    INVEST_PROFILE_SCORE       NUMBER           NULL, 
    INVEST_TRACING_SCORE       NUMBER           NULL, 
    KNOWLEDGE_SCORE            NUMBER           NULL, 
    KNOWLEDGE_COMMON_SCORE     NUMBER           NULL, 
    KNOWLEDGE_TYPE_SCORE       NUMBER           NULL, 
    KNOWLEDGE_CHANGE_SCORE     NUMBER           NULL, 
    KNOWLEDGE_SELL_SCORE       NUMBER           NULL, 
    AVG_UK_MASTERY             FLOAT            NULL, 
    USER_MBTI                  VARCHAR2(5)      NULL, 
    INVEST_ITEM_NUM            NUMBER           NULL, 
    STOCK_RATIO                NUMBER           NULL, 
    RECOMMEND_BASIC_LIST       VARCHAR2(500)    NULL, 
    RECOMMEND_TYPE_LIST        VARCHAR2(500)    NULL, 
    RECOMMEND_ADVANCED_LIST    VARCHAR2(500)    NULL, 
    DIAGNOSIS_DATE             TIMESTAMP        NOT NULL
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


CREATE TABLE ETEST.MINITEST_REPORT
(
    MINITEST_ID            VARCHAR2(36)      NOT NULL, 
    USER_UUID              VARCHAR2(36)      NOT NULL, 
    AVG_UK_MASTERY         FLOAT             NOT NULL, 
    SET_NUM                NUMBER            NOT NULL, 
    CORRECT_NUM            NUMBER            NULL, 
    WRONG_NUM              NUMBER            NULL, 
    DUNNO_NUM              NUMBER            NULL, 
    MINITEST_DATE          TIMESTAMP         NOT NULL, 
    MINITEST_UK_MASTERY    VARCHAR2(3000)    NULL
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
	IMG_SRC VARCHAR(256),
	TIME_RECOMMENDATION NUMBER,
	CREATOR_ID VARCHAR(32),
	CREATE_DATE TIMESTAMP(6),
	VALIDATOR_ID VARCHAR(32),
	VALIDATE_DATE TIMESTAMP(6),
	EDITOR_ID VARCHAR(32),
	EDIT_DATE TIMESTAMP(6),
	SOURCE VARCHAR(512),
	INTENTION VARCHAR(100),
	QUESTION_INITIAL VARCHAR(40000),
	SOLUTION_INITIAL VARCHAR(40000)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.PROBLEM_CHOICE (
	PROB_ID NUMBER NOT NULL,
	CHOICE_NUM NUMBER NOT NULL,
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

CREATE TABLE ETEST.TEST_PROBLEM (
	PROB_ID NUMBER NOT NULL,
	PART_ID NUMBER,
	SUBJECT VARCHAR(100),
	STATUS VARCHAR(20) NOT NULL
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

CREATE TABLE ETEST.UK_MASTER (
	UK_ID NUMBER NOT NULL,
	UK_NAME VARCHAR(200) NOT NULL,
	UK_DESCRIPTION VARCHAR(1000),
	EXTERNAL_LINK VARCHAR(2000),
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
    INQUIRY_ANSWER VARCHAR(2048),
    ADMIN_UUID VARCHAR(36)
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



CREATE SEQUENCE ETEST.NOTICE_SEQUENCE INCREMENT BY 1 START WITH 1;

CREATE SEQUENCE ETEST.FAQ_SEQUENCE INCREMENT BY 1 START WITH 1;

CREATE SEQUENCE ETEST.INQUIRY_SEQUENCE INCREMENT BY 1 START WITH 1;

CREATE TABLE ETEST.CS_INQUIRY_FILE (
	INQUIRY_FILE_ID NUMBER NOT NULL,
	INQUIRY_FILE_URL VARCHAR(100) NOT NULL,
	INQUIRY_FILE_TYPE VARCHAR(100),
    INQUIRY_FILE_NAME VARCHAR(255),
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

ALTER TABLE ETEST.DIAGNOSIS_CURRICULUM ADD CONSTRAINT PK_DIAGNOSIS_CURRICULUM
PRIMARY KEY (
	CURRICULUM_ID
);

ALTER TABLE ETEST.DIAGNOSIS_PROBLEM ADD CONSTRAINT PK_DIAGNOSIS_PROBLEM
PRIMARY KEY (
	PROB_ID
);

ALTER TABLE ETEST.DIAGNOSIS_REPORT ADD CONSTRAINT PK_DIAGNOSIS_REPORT
PRIMARY KEY (
	USER_UUID,
	DIAGNOSIS_DATE
);

ALTER TABLE ETEST.ERROR_REPORT ADD CONSTRAINT PK_ERROR_REPORT
PRIMARY KEY (
	ERROR_ID
);

ALTER TABLE ETEST.MINITEST_REPORT ADD CONSTRAINT ETEST_CON1286500084
PRIMARY KEY (
	USER_UUID,
	MINITEST_DATE
);

ALTER TABLE ETEST.PART ADD CONSTRAINT PK_PART
PRIMARY KEY (
	PART_ID
);

ALTER TABLE ETEST.PROBLEM ADD CONSTRAINT PK_PROBLEM
PRIMARY KEY (
	PROB_ID
);

ALTER TABLE ETEST.UK_MASTER ADD CONSTRAINT PK_UK_MASTER
PRIMARY KEY (
	UK_ID
);

ALTER TABLE ETEST.USER_KNOWLEDGE ADD CONSTRAINT PK_USER_KNOWLEDGE
PRIMARY KEY (
	USER_UUID,
	UK_ID
);

ALTER TABLE ETEST.USER_MASTER ADD CONSTRAINT PK_USER_MASTER
PRIMARY KEY (
	USER_UUID
);

ALTER TABLE ETEST.CS_NOTICE ADD CONSTRAINT PK_NOTICE_ID
PRIMARY KEY (
	NOTICE_ID
);

ALTER TABLE ETEST.CS_FAQ ADD CONSTRAINT PK_FAQ_ID
PRIMARY KEY (
	FAQ_ID
);

ALTER TABLE ETEST.CS_INQUIRY ADD CONSTRAINT PK_INQUIRY_ID
PRIMARY KEY (
	INQUIRY_ID
);

ALTER TABLE ETEST.DIAGNOSIS_PROBLEM
    ADD CONSTRAINT FK_DIAGNOSIS_PROBLEM_PROB_ID FOREIGN KEY (PROB_ID)
        REFERENCES ETEST.PROBLEM (PROB_ID);

ALTER TABLE ETEST.DIAGNOSIS_PROBLEM
    ADD CONSTRAINT FK_DIAGNOSIS_PROBLEM_CURRICULUM_ID FOREIGN KEY (CURRICULUM_ID)
        REFERENCES ETEST.DIAGNOSIS_CURRICULUM (CURRICULUM_ID);

ALTER TABLE ETEST.TEST_PROBLEM
    ADD CONSTRAINT FK_TEST_PROBLEM_PROB_ID FOREIGN KEY (PROB_ID)
        REFERENCES ETEST.PROBLEM (PROB_ID);

ALTER TABLE ETEST.TEST_PROBLEM
    ADD CONSTRAINT FK_TEST_PROBLEM_PART_ID FOREIGN KEY (PART_ID)
        REFERENCES ETEST.PART (PART_ID);

ALTER TABLE ETEST.PROBLEM_CHOICE
    ADD CONSTRAINT FK_PROBLEM_CHOICE_PROB_ID FOREIGN KEY (PROB_ID)
        REFERENCES ETEST.PROBLEM (PROB_ID);

ALTER TABLE ETEST.PROBLEM_CHOICE
    ADD CONSTRAINT FK_PROBLEM_CHOICE_UK_ID FOREIGN KEY (UK_ID)
        REFERENCES ETEST.UK_MASTER (UK_ID);

ALTER TABLE ETEST.PROBLEM_UK_REL
    ADD CONSTRAINT FK_PROBLEM_UK_REL_PROB_ID FOREIGN KEY (PROB_ID)
        REFERENCES ETEST.PROBLEM (PROB_ID);

ALTER TABLE ETEST.PROBLEM_UK_REL
    ADD CONSTRAINT FK_PROBLEM_UK_REL_UK_ID FOREIGN KEY (UK_ID)
        REFERENCES ETEST.UK_MASTER (UK_ID);

CREATE SEQUENCE ETEST.ERROR_SEQ INCREMENT BY 1 START WITH 1;

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
    VIDEO_ID             VARCHAR2(32)     NOT NULL, 
    VIDEO_SRC            VARCHAR2(256)    NOT NULL, 
    ORIGINAL_FILE_SRC    VARCHAR2(256)    NULL, 
    TITLE                VARCHAR2(512)    NOT NULL, 
    IMG_SRC              VARCHAR2(32)     NOT NULL, 
    CURRICULUM_ID        NUMBER           NOT NULL, 
    TOTAL_TIME           FLOAT            NOT NULL, 
    START_TIME           FLOAT            NULL, 
    END_TIME             FLOAT            NULL, 
    CREATE_DATE          DATE             NULL, 
    REGISTER_DATE        DATE             NULL, 
    END_DATE             DATE             NULL, 
    SEQUENCE             NUMBER           NOT NULL, 
    CODE_SET             VARCHAR2(512)    NULL, 
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
        REFERENCES ETEST.VIDEO_CURRICULUM (CURRICULUM_ID);


CREATE TABLE ETEST.BOOK
(
    BOOK_ID        NUMBER            NOT NULL, 
    BOOK_SRC       VARCHAR2(32)      NOT NULL, 
    TITLE          VARCHAR2(128)     NOT NULL, 
    CREATE_DATE    TIMESTAMP         NOT NULL, 
		CREATOR_ID     VARCHAR2(128)     NOT NULL,
    IMG_SRC        VARCHAR2(32)      NOT NULL, 
    DESCRIPTION    VARCHAR2(1024)    NOT NULL, 
		CODE_SET			 VARCHAR2(512)	   NULL,
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

CREATE TABLE ETEST.BOOK_HIT
(
    BOOK_ID    NUMBER    NOT NULL, 
    HIT        NUMBER          NOT NULL, 
     PRIMARY KEY (BOOK_ID)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
  MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;

ALTER TABLE ETEST.BOOK_HIT
    ADD CONSTRAINT FK_BOOK_HIT_BOOK_ID_BOOK_BOOK_ FOREIGN KEY (BOOK_ID)
        REFERENCES ETEST.BOOK (BOOK_ID);

CREATE TABLE ETEST.VIDEO_HIT
(
    VIDEO_ID    VARCHAR2(32)    NOT NULL, 
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
    VIDEO_ID    VARCHAR2(32)    NOT NULL, 
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
    VIDEO_ID     VARCHAR2(32)    NOT NULL, 
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
    STATEMENT_ID      VARCHAR2(100)     NOT NULL, 
    USER_ID           VARCHAR2(100)     NOT NULL, 
    ACTION_TYPE       VARCHAR2(100)     NOT NULL, 
    SOURCE_TYPE       VARCHAR2(100)     NOT NULL, 
    SOURCE_ID         VARCHAR2(100)     NULL, 
    STATEMENT_DATE    TIMESTAMP(6)      NULL, 
    TIMESTAMP         VARCHAR2(100)     NOT NULL, 
    PLATFORM          VARCHAR2(100)     NULL, 
    CURSOR_TIME       NUMBER            NULL, 
    IS_CORRECT        NUMBER            NULL, 
    IS_DELETED        NUMBER            NULL, 
    USER_ANSWER       VARCHAR2(500)     NULL, 
    DURATION          VARCHAR2(100)     NULL, 
    EXTENSION         VARCHAR2(2000)    NULL, 
    CONSTRAINT PK_STATEMENT_MASTER PRIMARY KEY (STATEMENT_ID)
)
LOGGING
TABLESPACE USR
PCTFREE 10
INITRANS 2;

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
    VIDEO_ID      VARCHAR2(32)    NOT NULL, 
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

CREATE SEQUENCE ETEST.HIT_STAT_SEQ INCREMENT BY 1 START WITH 1;

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
	references ETEST.BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
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
	references ETEST.BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
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
	references ETEST.BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE ETEST.BATCH_STEP_EXECUTION_CONTEXT  (
	STEP_EXECUTION_ID NUMBER(19,0) NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR2(2500 char) NOT NULL,
	SERIALIZED_CONTEXT CLOB ,
	constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
	references ETEST.BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ;

CREATE TABLE ETEST.BATCH_JOB_EXECUTION_CONTEXT  (
	JOB_EXECUTION_ID NUMBER(19,0) NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR2(2500 char) NOT NULL,
	SERIALIZED_CONTEXT CLOB ,
	constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
	references ETEST.BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE SEQUENCE ETEST.BATCH_STEP_EXECUTION_SEQ START WITH 0 MINVALUE 0 MAXVALUE 9223372036854775807 NOCYCLE;
CREATE SEQUENCE ETEST.BATCH_JOB_EXECUTION_SEQ START WITH 0 MINVALUE 0 MAXVALUE 9223372036854775807 NOCYCLE;
CREATE SEQUENCE ETEST.BATCH_JOB_SEQ START WITH 0 MINVALUE 0 MAXVALUE 9223372036854775807 NOCYCLE;

------ CREATE TABLE META_CODE_MASTER ------
CREATE TABLE ETEST.META_CODE_MASTER
(
    META_CODE_ID    VARCHAR2(32)     NOT NULL, 
    DOMAIN          VARCHAR2(100)    NOT NULL, 
    SEQUENCE        NUMBER           NULL, 
    CODE            VARCHAR2(100)    NOT NULL, 
    CODE_NAME       VARCHAR2(256)    NOT NULL, 
     PRIMARY KEY (META_CODE_ID)
)
TABLESPACE USR
PCTFREE 10
INITRANS 2
STORAGE (
	MAXEXTENTS UNLIMITED
)
LOGGING
NOPARALLEL;
