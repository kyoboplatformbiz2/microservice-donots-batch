package com.kyobo.platform.donots.batch.biz.parent.exception;

public class BusinessException extends RuntimeException {
    public final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
        this.code = ErrorCode.DEFAULT.status;
    }

    public BusinessException() {
        super(ErrorCode.DEFAULT.message);
        this.code = ErrorCode.DEFAULT.status;
    }
}
