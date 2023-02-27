package com.kyobo.platform.donots.batch.biz.parent.entity;

import lombok.*;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor // Builder를 위한 생성자 (NoArgsConstructor를 protected로 선언하면 오류 발생)
@NoArgsConstructor
@Builder
@Data
@ToString
public class BabyAllergyIngredient implements Serializable {

    @Id
    private String key;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BabyAllergyIngredient that = (BabyAllergyIngredient) o;
        return key.equals(that.key) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, name);
    }
}
