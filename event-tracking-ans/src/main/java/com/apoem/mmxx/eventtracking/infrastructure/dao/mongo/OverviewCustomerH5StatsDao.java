package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

import com.apoem.mmxx.eventtracking.infrastructure.po.dm.OverviewCustomerH5StatsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: OverviewCustomerH5StatsDao </p>
 * <p>Description: 运营后台-总览 </p>
 * <p>Date: 2020/8/28 17:54 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
public interface OverviewCustomerH5StatsDao extends MongoRepository<OverviewCustomerH5StatsEntity, Object> {

    /**
     * 条件查询
     *
     * @param city 城市
     * @param dateDay 日
     * @return 实体
     */
    OverviewCustomerH5StatsEntity findByCityAndDateDay(String city, Integer dateDay);

}
