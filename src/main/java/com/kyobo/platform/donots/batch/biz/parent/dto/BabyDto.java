package com.kyobo.platform.donots.batch.biz.parent.dto;

import com.kyobo.platform.donots.batch.biz.parent.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
// TODO 발생한 JsonProcessingException 해결하기 위한 시도 (기본 생성자 추가)
// com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `com.kyobo.platform.retrieve.bizModule.parent.dto.BabyDto` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Slf4j
public class BabyDto implements Comparable<BabyDto> {
    private Long key;
    private Long parentKey;
    private String nickname;
    private LocalDate birthdate;
    private String height;
    private String weight;
    private Gender gender;
    private String profilePictureUrl;
    private Integer profilePictureThumbnailOrder;
    @Builder.Default
    private List<BabyAllergyIngredient> allergyIngredients = new ArrayList<>();
    @Builder.Default
    private List<BabyConcern> concerns = new ArrayList<>();

    public static BabyDto from(Baby baby) {

        return BabyDto.builder()
                .key(baby.getKey())
                .parentKey(baby.getParent().getKey())   // Key로 저장하여 순환참조는 발생하지 않음
                .nickname(baby.getNickname())
                .birthdate(baby.getBirthdate())
                .height(baby.getHeight())
                .weight(baby.getWeight())
                .gender(baby.getGender())
                .profilePictureUrl(baby.getProfilePictureUrl())
                .profilePictureThumbnailOrder(baby.getProfilePictureThumbnailOrder())
                .allergyIngredients(baby.getAllergyIngredients())
                .concerns(baby.getConcerns())
                .build();
    }

    public Baby toEntity() {
        // JPA PersistenceContext에 혼란을 주지 않기 위해
        // parentKey가 없을 때는 의미없이 빈 Parent 인스턴스를 저장하지 않고 의도적으로 null로 저장한다
        Parent tempParent;
        if (parentKey == null)
            tempParent = null;
        else
            tempParent = Parent.builder().key(parentKey).build();

        return Baby.builder()
                .key(key)
                .parent(tempParent)
                .nickname(nickname)
                .gender(gender)
                .birthdate(birthdate)
                .height(height)
                .weight(weight)
                .profilePictureUrl(profilePictureUrl)
                .profilePictureThumbnailOrder(profilePictureThumbnailOrder)
                .allergyIngredients(allergyIngredients)
                .concerns(concerns)
                .build();
    }

    @Override
    public int compareTo(BabyDto babyDto) {
        return this.profilePictureThumbnailOrder - babyDto.getProfilePictureThumbnailOrder();
    }
}
