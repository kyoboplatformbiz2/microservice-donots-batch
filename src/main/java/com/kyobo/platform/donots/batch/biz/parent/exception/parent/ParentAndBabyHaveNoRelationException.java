package com.kyobo.platform.donots.batch.biz.parent.exception.parent;

import com.kyobo.platform.donots.batch.biz.parent.exception.BusinessException;
import com.kyobo.platform.donots.batch.biz.parent.exception.ErrorCode;

public class ParentAndBabyHaveNoRelationException extends BusinessException {

    public ParentAndBabyHaveNoRelationException() {
        super(ErrorCode.CAUSING_INVALID_BABY_COUNT.status, ErrorCode.CAUSING_INVALID_BABY_COUNT.message);
    }

    public ParentAndBabyHaveNoRelationException(String caseSpecificMessage) {
        super(ErrorCode.CAUSING_INVALID_BABY_COUNT.status, caseSpecificMessage);
    }
}
