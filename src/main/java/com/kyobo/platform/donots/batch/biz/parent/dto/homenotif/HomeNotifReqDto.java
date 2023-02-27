package com.kyobo.platform.donots.batch.biz.parent.dto.homenotif;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HomeNotifReqDto {
    private String noti_subject = "";           // 알림제목
    private String noti_type = "";              // 알림유형
    private String noti_recipe_key = "";        // 공지사항키
    private String noti_target_user_key = "";   // 스크링클유저키
}
