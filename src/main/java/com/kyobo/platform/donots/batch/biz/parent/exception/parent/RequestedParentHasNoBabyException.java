package com.kyobo.platform.donots.batch.biz.parent.exception.parent;

import com.kyobo.platform.donots.batch.biz.parent.exception.BusinessException;
import com.kyobo.platform.donots.batch.biz.parent.exception.ErrorCode;

public class RequestedParentHasNoBabyException extends BusinessException {

    public RequestedParentHasNoBabyException() {
        super(ErrorCode.REQUESTED_PARENT_HAS_NO_BABY.status, ErrorCode.REQUESTED_PARENT_HAS_NO_BABY.message);
    }

    public RequestedParentHasNoBabyException(String caseSpecificMessage) {
        super(ErrorCode.REQUESTED_PARENT_HAS_NO_BABY.status, caseSpecificMessage);
    }
}
