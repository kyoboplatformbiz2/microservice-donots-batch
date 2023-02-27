package com.kyobo.platform.donots.batch.biz.parent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kyobo.platform.donots.batch.biz.parent.entity.Baby;
import com.kyobo.platform.donots.batch.biz.parent.entity.Parent;
import com.kyobo.platform.donots.batch.biz.parent.entity.ParentGrade;
import com.kyobo.platform.donots.batch.biz.parent.entity.ParentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParentDto {
    private Long key;
    private Long accountKey;
    private ParentType type;
    private ParentGrade grade;
    private String nickname;
    private String email;
    private String profilePictureUrl;
    private String briefBio;
    private String socialMediaUrl;
    @Builder.Default
    private List<BabyDto> babies = new ArrayList<>();
    private Long profileSelectedBabyKey;

    // 마케팅 개인정보 수집 약관
    @JsonProperty("isTermsCollectingPersonalDataMarketingAgreed")
    private Boolean isTermsCollectingPersonalDataMarketingAgreed;

    // 전송매체별 광고성 정보의 수신 동의
    @JsonProperty("isEmailReceiveAgreed")
    private Boolean isEmailReceiveAgreed;
    private LocalDateTime emailReceiveAgreementModifiedDatetime;

    @JsonProperty("isTextMessageReciveAgreed")
    private Boolean isTextMessageReciveAgreed;
    private LocalDateTime textMessageReciveAgreementModifiedDatetime;

    // 설정 > Push 설정 > 마케팅 알림 수신
    @JsonProperty("isMarketingInfoPushNotifSet")
    private Boolean isMarketingInfoPushNotifSet;
    private LocalDateTime marketingInfoPushNotifSettingModifiedDatetime;

    // 설정 > Push 설정 > 작성 콘텐츠 등록/검수 결과
    @JsonProperty("isPostCensorshipResultPushNotifSet")
    private Boolean isPostCensorshipResultPushNotifSet;

    public static ParentDto from(Parent parent) {
        List<BabyDto> babyDtosFromEntity = new ArrayList<>();
        for (Baby baby : parent.getBabies())
            babyDtosFromEntity.add(BabyDto.from(baby));

        // 클라이언트의 요청에 따라 ResponseBody에 들어가는 아이 List 인덱스 순서를 썸네일 순서 오름차순으로 정렬하여 전달해주도록 함
        // 이 DTO를 사용하지 않는 모든 다른 API에서는 List 인덱스 순서가 보장되지 않음
        Collections.sort(babyDtosFromEntity);

        return ParentDto.builder()
                .key(parent.getKey())
                .accountKey(parent.getAccountKey())
                .type(parent.getType())
                .grade(parent.getGrade())
                .nickname(parent.getNickname())
                .email(parent.getEmail())
                .profilePictureUrl(parent.getProfilePictureUrl())
                .briefBio(parent.getBriefBio())
                .socialMediaUrl(parent.getSocialMediaUrl())
                .babies(babyDtosFromEntity)
                .profileSelectedBabyKey(parent.getProfileSelectedBabyKey())
                .isTermsCollectingPersonalDataMarketingAgreed(parent.getIsTermsCollectingPersonalDataMarketingAgreed())
                .isEmailReceiveAgreed(parent.getIsEmailReceiveAgreed())
                .emailReceiveAgreementModifiedDatetime(parent.getEmailReceiveAgreementModifiedDatetime())
                .isTextMessageReciveAgreed(parent.getIsTextMessageReciveAgreed())
                .textMessageReciveAgreementModifiedDatetime(parent.getTextMessageReciveAgreementModifiedDatetime())
                .isMarketingInfoPushNotifSet(parent.getIsMarketingInfoPushNotifSet())
                .marketingInfoPushNotifSettingModifiedDatetime(parent.getMarketingInfoPushNotifSettingModifiedDatetime())
                .isPostCensorshipResultPushNotifSet(parent.getIsPostCensorshipResultPushNotifSet())
                .build();
    }

    public Parent toEntity() {
        List<Baby> babyEntitiesFromDto = new ArrayList<>();
        for (BabyDto babyDto : babies)
            babyEntitiesFromDto.add(babyDto.toEntity());

        return Parent.builder()
                .key(key)
                .accountKey(accountKey)
                .nickname(nickname)
                .email(email)
                .profilePictureUrl(profilePictureUrl)
                .briefBio(briefBio)
                .socialMediaUrl(socialMediaUrl)
                // TODO 연관관계 주인이 아니라 셋팅하는 것이 의미가 없을 것 같은데 추후 테스트
                .babies(babyEntitiesFromDto)
                .profileSelectedBabyKey(profileSelectedBabyKey)
                .isTermsCollectingPersonalDataMarketingAgreed(isTermsCollectingPersonalDataMarketingAgreed)
                .isEmailReceiveAgreed(isEmailReceiveAgreed)
                .emailReceiveAgreementModifiedDatetime(emailReceiveAgreementModifiedDatetime)
                .isTextMessageReciveAgreed(isTextMessageReciveAgreed)
                .textMessageReciveAgreementModifiedDatetime(textMessageReciveAgreementModifiedDatetime)
                .isMarketingInfoPushNotifSet(isMarketingInfoPushNotifSet)
                .marketingInfoPushNotifSettingModifiedDatetime(marketingInfoPushNotifSettingModifiedDatetime)
                .isPostCensorshipResultPushNotifSet(isPostCensorshipResultPushNotifSet)
                .build();
    }
}
