package com.kyobo.platform.donots.batch.biz.parent.controller;

import com.kyobo.platform.donots.batch.biz.parent.service.ParentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@Slf4j
public class ParentController {
    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @Operation(summary = "모든 회원 > 활동지표 집계정보요청 to Recipe, 갱신")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @PostMapping("/v1/members/activity-indicator/aggregate")
    public ResponseEntity<?> requestActivityIndicatorsOfAllParents() throws IOException, ParseException {
        log.info("ParentGradeController.requestActivityIndicatorsOfAllParents");

        parentService.requestActivityIndicatorsOfAllParents();

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모든 회원 > 등급산정 및 갱신, 승급알림")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @PostMapping("/v1/members/grade")
    public ResponseEntity<?> gradeAllParents() throws IOException {
        log.info("ParentGradeController.gradeAllParents");

        parentService.gradeAllParents();

        return ResponseEntity.ok().build();
    }
}
