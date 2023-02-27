package com.kyobo.platform.donots.batch.biz.parent.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    GENERAL(1000, "정의되지 않은 오류입니다."),
    DATA_NOT_FOUND(1001, "조회된 데이터가 없습니다."),
    REQUEST_BODY_IS_EMPTY(1002, "요청된 RequestBody의 내용이 없습니다."),
    VALID_PARAMETER(1003, "파라메터 인자값이 정상적이지 않습니다."),
    PARENT_NOT_FOUND(2001, "존재하지 않는 회원입니다."),
    REQUESTED_PARENT_HAS_NO_BABY(2002, "생성요청한 회원정보가 아이정보를 가지고 있지 않습니다."),
    REQUESTED_PARENT_GRADE_NOT_EXIST(2003, "요청한 회원등급은 존재하지 않습니다."),
    CAUSING_INVALID_BABY_COUNT(2004, "유효하지 않은 아이 숫자를 만드는 요청입니다. (유효한 아이 숫자: 1~3)"),
    PARENT_AND_BABY_HAVE_NO_RELATION(2005, "회원과 관계없는 아이입니다."),
    BABY_NOT_FOUND(2101, "존재하지 않는 아이입니다."),
    TERMS_OF_SERVICE_NOT_FOUND(2201,"존재하지 않는 서비스약관입니다."),
    FAQ_POST_NOT_FOUND(2301,"존재하지 않는 FAQ 게시물입니다."),
    NOTICE_POST_NOT_FOUND(2401, "존재하지 않는 공지사항 게시물입니다."),
    INVALID_ACCESS_TOKEN(4001,"잘못된 토큰입니다. 다시 토큰을 발급 받으십시요."),
    INVALID_REFRESH_TOKEN(4002,"만료된 토큰입니다. 토큰을 갱신하세요."),
    ACCOUNT_NOT_FOUND(4003, "가입된 계정이 없습니다."),
    PASSWORD_NOT_MATCH(4004, "비밀번호가 틀립니다."),
    NO_ACCOUNT_LINKED(4005, "연결된 계정이 없습니다"),
    PASSWORD_SAME(4006, "기존 패스워드와 동일합니다."),
    PASSWORD_INCLUDE_PERSONAL_INFORMATION(4007, "비밀번호에 개인정보가 포함되어 있습니다."),
    ALREADY_REGISTERED_ID(4008, "이미 가입된 아이디입니다"),
    ALREADY_REGISTERED_CI(4009, "이미 가입된 CI입니다"),
    ENCRYPTION_KEY_FOUND(4010, "암호화 키가 없습니다."),
    PERMISSION_NOT_FOUND(4011, "권한이 없습니다."),
    ONE_ACCOUNT_DISCONNECT_FAIL(4012, "연결 해제할 계정이 1개 밖에 없어, 연결 해제가 실패 했습니다."),
    UUID_FORMAT_DIFFERENT(4013, "UUID 형식이 맞지 않습니다."),
    EXTERNAL_SERVER_KCB(5001, "휴대폰 본인 인증 서버가 문제가 있습니다."),
    EXTERNAL_SERVER_KAKAO(5002, "KAKAO 인증서버에 문제가 있습니다."),
    EXTERNAL_SERVER_NAVER(5003, "NAVER 인증서버에 문제가 있습니다."),
    EXTERNAL_SERVER_GOOGLE(5004, "GOOGLE 인증서버에 문제가 있습니다."),
    EXTERNAL_SERVER_APPLE(5005, "GOOGLE 인증서버에 문제가 있습니다."),
    EXTERNAL_SERVER_ENCRYPT(5006, "암호키 저장 서버에 문제가 있습니다."),
    JSON_PROCESSING_ERROR(9001, "JSON 처리 오류입니다."),
    INVALID_IMAGE_URL(9002, "유효하지 않은 이미지 URL입니다."),
    DEFAULT(500, "정의되지 않은 에러");

    public final int status;
    public final String message;
}

