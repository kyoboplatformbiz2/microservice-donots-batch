package com.kyobo.platform.donots.batch.biz.parent.exception.common;

import com.kyobo.platform.donots.batch.biz.parent.exception.BusinessException;
import com.kyobo.platform.donots.batch.biz.parent.exception.ErrorCode;

public class RequestBodyEmptyException extends BusinessException {

    public RequestBodyEmptyException() {
        super(ErrorCode.REQUEST_BODY_IS_EMPTY.status, ErrorCode.REQUEST_BODY_IS_EMPTY.message);
    }

    public RequestBodyEmptyException(String caseSpecificMessage) {
        super(ErrorCode.REQUEST_BODY_IS_EMPTY.status, caseSpecificMessage);
    }
}
