package com.kyobo.platform.donots.batch.biz.parent.exception.common;

import com.kyobo.platform.donots.batch.biz.parent.exception.BusinessException;
import com.kyobo.platform.donots.batch.biz.parent.exception.ErrorCode;

public class InvalidImageUrlException extends BusinessException {

    public InvalidImageUrlException() {
        super(ErrorCode.INVALID_IMAGE_URL.status, ErrorCode.INVALID_IMAGE_URL.message);
    }

    public InvalidImageUrlException(String caseSpecificMessage) {
        super(ErrorCode.INVALID_IMAGE_URL.status, caseSpecificMessage);
    }
}
