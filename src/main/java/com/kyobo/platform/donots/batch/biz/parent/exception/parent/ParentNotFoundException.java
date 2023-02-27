package com.kyobo.platform.donots.batch.biz.parent.exception.parent;

import com.kyobo.platform.donots.batch.biz.parent.exception.BusinessException;
import com.kyobo.platform.donots.batch.biz.parent.exception.ErrorCode;

public class ParentNotFoundException extends BusinessException {

    public ParentNotFoundException() {
        super(ErrorCode.PARENT_NOT_FOUND.status, ErrorCode.PARENT_NOT_FOUND.message);
    }

    public ParentNotFoundException(String caseSpecificMessage) {
        super(ErrorCode.PARENT_NOT_FOUND.status, caseSpecificMessage);
    }
}
