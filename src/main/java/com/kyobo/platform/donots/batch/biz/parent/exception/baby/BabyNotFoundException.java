package com.kyobo.platform.donots.batch.biz.parent.exception.baby;

import com.kyobo.platform.donots.batch.biz.parent.exception.BusinessException;
import com.kyobo.platform.donots.batch.biz.parent.exception.ErrorCode;

public class BabyNotFoundException extends BusinessException {

    public BabyNotFoundException() {
        super(ErrorCode.BABY_NOT_FOUND.status, ErrorCode.BABY_NOT_FOUND.message);
    }

    public BabyNotFoundException(String caseSpecificMessage) {
        super(ErrorCode.BABY_NOT_FOUND.status, caseSpecificMessage);
    }
}
