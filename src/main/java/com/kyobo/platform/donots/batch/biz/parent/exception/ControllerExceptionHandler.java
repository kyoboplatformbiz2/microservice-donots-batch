package com.kyobo.platform.donots.batch.biz.parent.exception;

import com.kyobo.platform.donots.batch.biz.parent.exception.baby.BabyNotFoundException;
import com.kyobo.platform.donots.batch.biz.parent.exception.common.GeneralException;
import com.kyobo.platform.donots.batch.biz.parent.exception.common.InvalidImageUrlException;
import com.kyobo.platform.donots.batch.biz.parent.exception.common.RequestBodyEmptyException;
import com.kyobo.platform.donots.batch.biz.parent.exception.parent.CausingInvalidBabyCountException;
import com.kyobo.platform.donots.batch.biz.parent.exception.parent.ParentAndBabyHaveNoRelationException;
import com.kyobo.platform.donots.batch.biz.parent.exception.parent.ParentNotFoundException;
import com.kyobo.platform.donots.batch.biz.parent.exception.parent.RequestedParentHasNoBabyException;
import com.kyobo.platform.donots.batch.biz.parent.exception.parentgrade.RequestedParentGradeNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    //------------------------------------------------------------------------------------------------------------------
    // 표준
    //------------------------------------------------------------------------------------------------------------------
//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse =
//                new ExceptionResponse(new Date(), 400 , ex.getMessage(), request.getDescription(false) );
//        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorCode.VALID_PARAMETER.getStatus(), ErrorCode.VALID_PARAMETER.getMessage(), ex.getBindingResult().toString() );

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    //------------------------------------------------------------------------------------------------------------------
    // 공통
    //------------------------------------------------------------------------------------------------------------------
    @ExceptionHandler(GeneralException.class)
    public final ResponseEntity<Object> generalException(BusinessException be, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), be, request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RequestBodyEmptyException.class)
    public final ResponseEntity<Object> requestBodyEmptyException(BusinessException be, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), be, request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    //------------------------------------------------------------------------------------------------------------------
    // 회원
    //------------------------------------------------------------------------------------------------------------------
    @ExceptionHandler(ParentNotFoundException.class)
    public final ResponseEntity<Object> parentNotFoundException(BusinessException be, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), be, request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BabyNotFoundException.class)
    public final ResponseEntity<Object> babyNotFoundException(BusinessException be, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), be, request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestedParentHasNoBabyException.class)
    public final ResponseEntity<Object> requestedParentHasNoBabyException(BusinessException be, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), be, request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CausingInvalidBabyCountException.class)
    public final ResponseEntity<Object> causingInvalidBabyCountException(BusinessException be, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), be, request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParentAndBabyHaveNoRelationException.class)
    public final ResponseEntity<Object> parentAndBabyHaveNoRelationException(BusinessException be, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), be, request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidImageUrlException.class)
    public final ResponseEntity<Object> invalidImageUrlException(BusinessException be, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), be, request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    //------------------------------------------------------------------------------------------------------------------
    // 회원등급
    //------------------------------------------------------------------------------------------------------------------
    @ExceptionHandler(RequestedParentGradeNotExistException.class)
    public final ResponseEntity<Object> requestedParentGradeNotExistException(BusinessException be, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), be, request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
