package com.kyobo.platform.donots.batch.biz.parent.dto;

import com.kyobo.platform.donots.batch.biz.parent.entity.Parent;
import com.kyobo.platform.donots.batch.biz.parent.entity.ParentGrade;
import com.kyobo.platform.donots.batch.biz.parent.entity.ParentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodaysParentRankingDto implements Comparable<TodaysParentRankingDto> {

    private Long memberKey;
    private ParentType type;
    private ParentGrade grade;
    private String nickname;
    private String profilePictureUrl;
    private String briefBio;
    @Builder.Default private Integer pageviewedCount = 0;
    @Builder.Default private Integer recipePostingCount = 0;
    @Builder.Default private Integer scrapbookedCount = 0;
    @Builder.Default private Integer reactionAddingCount = 0;
    @Builder.Default private Integer scrapbookingCount = 0;

    public static TodaysParentRankingDto from(Parent parent) {

        return TodaysParentRankingDto.builder()
                .memberKey(parent.getKey())
                .type(parent.getType())
                .grade(parent.getGrade())
                .nickname(parent.getNickname())
                .profilePictureUrl(parent.getProfilePictureUrl())
                .briefBio(parent.getBriefBio())
                .pageviewedCount(parent.getPageviewedCount())
                .recipePostingCount(parent.getRecipePostingCount())
                .scrapbookedCount(parent.getScrapbookedCount())
                .reactionAddingCount(parent.getReactionAddingCount())
                .scrapbookingCount(parent.getScrapbookingCount())
                .build();
    }

    @Override
    public int compareTo(TodaysParentRankingDto todaysParentRankingDto) {
        int diffOf1stOrderCriterion = this.pageviewedCount - todaysParentRankingDto.getPageviewedCount();
        int diffOf2ndOrderCriterion = this.recipePostingCount - todaysParentRankingDto.getRecipePostingCount();
        int diffOf3rdOrderCriterion = this.scrapbookedCount - todaysParentRankingDto.getScrapbookedCount();

        // 내림차순으로 정렬하기 위해 결과 값에 마이너스를 붙인다.
        if (diffOf1stOrderCriterion != 0) {
            return -diffOf1stOrderCriterion;
        }
        else if (diffOf2ndOrderCriterion != 0) {
            return -diffOf2ndOrderCriterion;
        }
        else if (diffOf3rdOrderCriterion != 0) {
            return -diffOf3rdOrderCriterion;
        }
        else {
            return 0;
        }
    }
}
