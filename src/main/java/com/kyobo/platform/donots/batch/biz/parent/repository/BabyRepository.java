package com.kyobo.platform.donots.batch.biz.parent.repository;

import com.kyobo.platform.donots.batch.biz.parent.entity.Baby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BabyRepository extends JpaRepository<Baby, Long> {
}
