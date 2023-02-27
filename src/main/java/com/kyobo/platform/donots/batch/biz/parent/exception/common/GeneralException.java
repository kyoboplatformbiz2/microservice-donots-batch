package com.kyobo.platform.donots.batch.biz.parent.exception.common;

import com.kyobo.platform.donots.batch.biz.parent.exception.BusinessException;
import com.kyobo.platform.donots.batch.biz.parent.exception.ErrorCode;

public class GeneralException extends BusinessException {

    public GeneralException() {
        super(ErrorCode.GENERAL.status, ErrorCode.GENERAL.message);
    }

    public GeneralException(String caseSpecificMessage) {
        super(ErrorCode.GENERAL.status, caseSpecificMessage);
    }
}
