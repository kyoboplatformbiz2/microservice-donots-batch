package com.kyobo.platform.donots.batch.biz.parent.exception.parentgrade;

import com.kyobo.platform.donots.batch.biz.parent.exception.BusinessException;
import com.kyobo.platform.donots.batch.biz.parent.exception.ErrorCode;

public class RequestedParentGradeNotExistException extends BusinessException {

    public RequestedParentGradeNotExistException() {
        super(ErrorCode.REQUESTED_PARENT_GRADE_NOT_EXIST.status, ErrorCode.REQUESTED_PARENT_GRADE_NOT_EXIST.message);
    }

    public RequestedParentGradeNotExistException(String caseSpecificMessage) {
        super(ErrorCode.REQUESTED_PARENT_GRADE_NOT_EXIST.status, caseSpecificMessage);
    }
}
