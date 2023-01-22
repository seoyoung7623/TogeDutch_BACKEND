package com.proj.togedutch.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),
    USERS_EMPTY_PASSWORD(false, 2011, "비밀번호 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_USERS_EMPTY_PASSWORD(false, 2018, "비밀번호를 입력해주세요."),

    // [POST] /post
    POST_POST_EMPTY_TITLE(false, 2019, "공고 제목을 입력해주세요."),
    POST_POST_EMPTY_URL(false, 2020, "URL을 입력해주세요."),
    POST_POST_EMPTY_TIP(false, 2021, "배달팁을 입력해주세요."),
    POST_POST_EMPTY_MINIMUM(false, 2022, "최소 주문 금액을 입력해주세요."),
    POST_POST_EMPTY_RECRUIT(false, 2023, "모집 인원을 선택해주세요."),
    POST_POST_EMPTY_LOCATION(false, 2024, "약속 장소를 설정해주세요."),

    // [POST] /Advertisement
    POST_AD_EMPTY_STORE(false, 2025, "가게 이름을 입력해주세요."),
    POST_AD_EMPTY_INFORMATION(false, 2026, "가게 정보를 입력해주세요."),
    POST_AD_EMPTY_MAINMENU(false, 2027, "메인 메뉴를 입력해주세요."),
    POST_AD_EMPTY_TIP(false, 2028, "배달팁을 입력해주세요."),
    POST_AD_EMPTY_LOCATION(false, 2029, "약속 장소를 설정해주세요."),

    // [POST] /notice
    POST_NOTICE_EMPTY_TITLE(false, 2030, "공지 제목을 설정해주세요."),
    POST_NOTICE_EMPTY_CONTENT(false, 2031, "공지 내용을 설정해주세요."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),
    FAILED_TO_FIND_USER(false, 3015, "존재하지 않는 회원입니다. 회원가입이 필요합니다."),

    // Post
    DELETE_POST_FAIL(false, 5000, "삭제에 실패했습니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USER(false,4014,"유저 수정 실패"),
    MODIFY_FAIL_KEYWORD(false,4015,"키워드 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    DELETE_FAIL_USER(false,4020,"유저 삭제 실패");

    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
