package com.kyobo.platform.donots.batch.biz.parent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ActivityIndicator {
    @Builder.Default private Integer pageviewedCount = 0;
    @Builder.Default private Integer recipePostingCount = 0;
    @Builder.Default private Integer scrapbookedCount = 0;
    @Builder.Default private Integer reactionAddingCount = 0;
    @Builder.Default private Integer scrapbookingCount = 0;
}
