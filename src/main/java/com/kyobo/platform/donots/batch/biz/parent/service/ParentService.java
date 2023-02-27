package com.kyobo.platform.donots.batch.biz.parent.service;

import com.kyobo.platform.donots.batch.biz.parent.entity.Parent;
import com.kyobo.platform.donots.batch.biz.parent.entity.ParentGrade;
import com.kyobo.platform.donots.batch.biz.parent.exception.common.GeneralException;
import com.kyobo.platform.donots.batch.biz.parent.repository.ParentRepository;
import com.kyobo.platform.donots.batch.config.HttpConfig;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
@Slf4j
public class ParentService {
    private final ParentRepository parentRepository;

    private final EntityManager em;

    // FIXME [TEST] 임시
//    @Value("${spring.profiles.active}")
    String env_active = "dev";

    public ParentService(ParentRepository parentRepository, EntityManager em) {
        this.parentRepository = parentRepository;

        this.em = em;
    }

    public Properties loadProperty() throws IOException {
        Properties properties = new Properties();
        Resource newResource = new ClassPathResource("awsAuth-" + env_active + ".properties");
        BufferedReader br = new BufferedReader(new InputStreamReader(newResource.getInputStream()));
        properties.load(br);

        return properties;
    }

    @Transactional
    public void requestActivityIndicatorsOfAllParents() throws IOException, ParseException {

        String sampleJson = "{\n" +
                "    \"select_recipe_statistic\": [\n" +
                "        {\n" +
                "            \"recipe_user_key\": \"1\",\n" +
                "            \"recipe_cnt\": 15,\n" +
                "            \"recipe_view_cnt\": 0,\n" +
                "            \"recipe_scrap_cnt\": 100\n" +
                "        },\n" +
                "        {\n" +
                "            \"recipe_user_key\": \"3\",\n" +
                "            \"recipe_cnt\": 100,\n" +
                "            \"recipe_view_cnt\": 1,\n" +
                "            \"recipe_scrap_cnt\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"recipe_user_key\": \"5\",\n" +
                "            \"recipe_cnt\": 101,\n" +
                "            \"recipe_view_cnt\": 1,\n" +
                "            \"recipe_scrap_cnt\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"recipe_user_key\": \"7\",\n" +
                "            \"recipe_cnt\": 101,\n" +
                "            \"recipe_view_cnt\": 2,\n" +
                "            \"recipe_scrap_cnt\": 1\n" +
                "        }\n" +
                "    ],\n" +
                "    \"selectRecipeReviewStatistic\": [\n" +
                "        {\n" +
                "            \"recipe_review_user_key\": \"1\",\n" +
                "            \"recipe_review_cnt\": 10\n" +
                "        },\n" +
                "        {\n" +
                "            \"recipe_review_user_key\": \"3\",\n" +
                "            \"recipe_review_cnt\": 100\n" +
                "        },\n" +
                "        {\n" +
                "            \"recipe_review_user_key\": \"5\",\n" +
                "            \"recipe_review_cnt\": 55\n" +
                "        },\n" +
                "        {\n" +
                "            \"recipe_review_user_key\": \"999\",\n" +
                "            \"recipe_review_cnt\": 999999\n" +
                "        }\n" +
                "    ],\n" +
                "    \"selectRecipeScrapStatistic\": [\n" +
                "        {\n" +
                "            \"recipe_scrap_user_key\": \"1\",\n" +
                "            \"recipe_scrap_cnt\": 15\n" +
                "        },\n" +
                "        {\n" +
                "            \"recipe_scrap_user_key\": \"3\",\n" +
                "            \"recipe_scrap_cnt\": 100\n" +
                "        },\n" +
                "        {\n" +
                "            \"recipe_scrap_user_key\": \"5\",\n" +
                "            \"recipe_scrap_cnt\": 55\n" +
                "        },\n" +
                "        {\n" +
                "            \"recipe_scrap_user_key\": \"7\",\n" +
                "            \"recipe_scrap_cnt\": 77\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(sampleJson);

        // 홈 > 알림 API 호출 시작
        String recipeurl = loadProperty().getProperty("recipeurl");
        String url = recipeurl + "/v1/admin/selectRecipeStatistic";
        //http://k8s-eksrecipeadmingro-b330c393ac-432154376.ap-northeast-2.elb.amazonaws.com/v1/admin/selectRecipeStatistic

        // FIXME [TEST]
        JSONObject responsedActivityIndicators;
        if (recipeurl.contains("192.168.1")) {
            responsedActivityIndicators = (JSONObject) obj;
        }
        else {
            responsedActivityIndicators = new HttpConfig().callApi(new JSONObject(), url, HttpMethod.GET.name());
            // TODO 예외처리
            if (responsedActivityIndicators == null)
                throw new GeneralException("Recipe API 처리시 오류 발생");
        }

        log.info("responsedActivityIndicators: "+ responsedActivityIndicators);

        JSONArray todaysParentRankingOrderCriteriaListByParent = (JSONArray) responsedActivityIndicators.get("select_recipe_statistic");
        log.info("todaysParentRankingOrderCriterionListByParent: "+ todaysParentRankingOrderCriteriaListByParent);
        if (!CollectionUtils.isEmpty(todaysParentRankingOrderCriteriaListByParent)) {

            for (int i = 0; i < todaysParentRankingOrderCriteriaListByParent.size(); i++) {
                JSONObject jsonElem = (JSONObject) todaysParentRankingOrderCriteriaListByParent.get(i);

                // TODO 유효성 검사
                Long parentKey = Long.parseLong(jsonElem.get("recipe_user_key").toString());
                Integer pageviewedCount = Integer.parseInt(jsonElem.get("recipe_view_cnt").toString());
                Integer recipePostingCount = Integer.parseInt(jsonElem.get("recipe_cnt").toString());
                Integer scrapbookedCount = Integer.parseInt(jsonElem.get("recipe_scrap_cnt").toString());

                // parentKey가 없을 때 익셉션을 터뜨리면 안 되고, DB 저장만 Skip 해야한다.
                Optional<Parent> nullableFoundParent = parentRepository.findById(parentKey);

                if (nullableFoundParent.isPresent()) {
                    Parent foundParent = nullableFoundParent.get();
                    em.persist(foundParent);

                    foundParent.setPageviewedCount(pageviewedCount);
                    foundParent.setRecipePostingCount(recipePostingCount);
                    foundParent.setScrapbookedCount(scrapbookedCount);
                    // TODO 추후 집계시간 한 번만 기록하도록 수정
                    foundParent.setLastActivityIndicatorAggregatedDatetime(LocalDateTime.now());
                }
            }
        }

        JSONArray reactionAddingCountListByParent = (JSONArray) responsedActivityIndicators.get("selectRecipeReviewStatistic");
        log.info("reactionAddingCountListByParent: "+ reactionAddingCountListByParent);
        if (!CollectionUtils.isEmpty(reactionAddingCountListByParent)) {

            for (int i = 0; i < reactionAddingCountListByParent.size(); i++) {
                JSONObject jsonElem = (JSONObject) reactionAddingCountListByParent.get(i);

                // TODO 유효성 검사
                Long parentKey = Long.parseLong(jsonElem.get("recipe_review_user_key").toString());
                Integer reactionAddingCount = Integer.parseInt(jsonElem.get("recipe_review_cnt").toString());

                // parentKey가 없을 때 익셉션을 터뜨리면 안 되고, DB 저장만 Skip 해야한다.
                Optional<Parent> nullableFoundParent = parentRepository.findById(parentKey);

                if (nullableFoundParent.isPresent()) {
                    Parent foundParent = nullableFoundParent.get();
                    em.persist(foundParent);

                    foundParent.setReactionAddingCount(reactionAddingCount);
                    // TODO 추후 집계시간 한 번만 기록하도록 수정
                    foundParent.setLastActivityIndicatorAggregatedDatetime(LocalDateTime.now());
                }
            }
        }

        JSONArray scrapbookingCountListByParent = (JSONArray) responsedActivityIndicators.get("selectRecipeScrapStatistic");
        log.info("scrapbookingCountListByParent: "+ scrapbookingCountListByParent);
        if (!CollectionUtils.isEmpty(scrapbookingCountListByParent)) {

            for (int i = 0; i < scrapbookingCountListByParent.size(); i++) {
                JSONObject jsonElem = (JSONObject) scrapbookingCountListByParent.get(i);

                // TODO 유효성 검사
                Long parentKey = Long.parseLong(jsonElem.get("recipe_scrap_user_key").toString());
                Integer scrapbookingCount = Integer.parseInt(jsonElem.get("recipe_scrap_cnt").toString());

                // parentKey가 없을 때 익셉션을 터뜨리면 안 되고, DB 저장만 Skip 해야한다.
                Optional<Parent> nullableFoundParent = parentRepository.findById(parentKey);

                if (nullableFoundParent.isPresent()) {
                    Parent foundParent = nullableFoundParent.get();
                    em.persist(foundParent);

                    foundParent.setScrapbookingCount(scrapbookingCount);
                    // TODO 추후 집계시간 한 번만 기록하도록 수정
                    foundParent.setLastActivityIndicatorAggregatedDatetime(LocalDateTime.now());
                }
            }
        }
    }

    @Transactional
    public void gradeAllParents() throws IOException {

        List<Parent> foundParents = parentRepository.findAll();
        for (Parent foundParent : foundParents) {
            log.info("Before grade calculation");
            log.info("Parent"+ foundParent.getKey()+ ": "+ foundParent.getGrade());
            foundParent.setGrade(
                    ParentGrade.calculateSatisfiedGrade(
                            foundParent.getGrade().name(),
                            foundParent.getRecipePostingCount(),
                            foundParent.getScrapbookedCount(),
                            foundParent.getReactionAddingCount(),
                            foundParent.getScrapbookingCount()
                    )
            );
            log.info("After grade calculation");
            log.info("Parent"+ foundParent.getKey()+ ": "+ foundParent.getGrade());
            em.persist(foundParent);

            String promotionNotifMessage = "";
            switch (foundParent.getGrade()) {
                case LV2:
                    promotionNotifMessage = "축하드려요! "+ foundParent.getNickname() +"님이 '"+ foundParent.getGrade() +"'등급으로 등업되었어요.";
                    break;
                case LV3:
                    promotionNotifMessage = "와우! "+ foundParent.getNickname() +"님이 '"+ foundParent.getGrade() +"'등급으로 등업되었어요.";
                    break;
                default:
                    // TODO 예외처리
            }

            String recipeurl = loadProperty().getProperty("recipeurl");
            String url = recipeurl + "/v1/recipe/registration/registMemberNoti";

            JSONObject promotionNotifRequest = new JSONObject();
            promotionNotifRequest.put("noti_img_key", "");                              // 사용되지 않는 필드지만 API 스펙에 따라 셋팅
            promotionNotifRequest.put("noti_recipe_key", "");                           // 사용되지 않는 필드지만 API 스펙에 따라 셋팅
            promotionNotifRequest.put("noti_subject", promotionNotifMessage);           // 알림 메시지
            promotionNotifRequest.put("noti_type", "승급");                             // 알림 유형 (승급, 공지사항)
            promotionNotifRequest.put("noti_target_user_key", foundParent.getKey().toString());    // 알림 대상 회원키

            JSONObject promotionNotifResponse = new HttpConfig().callApi(promotionNotifRequest, url, HttpMethod.POST.name());
            // TODO 예외처리
            if (promotionNotifResponse == null)
                throw new GeneralException("Recipe API 처리시 오류 발생");

            String promotionNotifResponseValue = (String) promotionNotifResponse.get("databody");
            log.info("promotionNotifResponseValue: "+ promotionNotifResponseValue);
            if (promotionNotifResponseValue == null)
                throw new GeneralException("Recipe API 처리시 오류 발생");
        }
    }
}
