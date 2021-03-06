package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.ods;

import com.apoem.mmxx.eventtracking.infrastructure.enums.EventTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.common.holder.CurrentRequestHolder;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.AbstractAutoIncrIdDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.MongoGeneratedKeyHandler;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.TrackPointEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.ods.BusinessAcquisitionOdsEntity;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: BusinessAcquisitionOdsDao </p>
 * <p>Description: B端ODS访问接口 </p>
 * <p>Date: 2020/7/14 12:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Repository
@Slf4j
@Accessors(fluent = true)
public class BusinessAcquisitionOdsDao extends AbstractAutoIncrIdDao<BusinessAcquisitionOdsEntity> {

    @Getter
    private final MongoTemplate mongoTemplate;
    @Getter
    private final MongoGeneratedKeyHandler mongoGeneratedKeyHandler;

    @Autowired
    public BusinessAcquisitionOdsDao(MongoTemplate mongoTemplate, MongoGeneratedKeyHandler mongoGeneratedKeyHandler) {
        this.mongoTemplate = mongoTemplate;
        this.mongoGeneratedKeyHandler = mongoGeneratedKeyHandler;
    }

    @Override
    public BusinessAcquisitionOdsEntity insertEntity(BusinessAcquisitionOdsEntity entity) {
        BusinessAcquisitionOdsEntity result = super.insertEntity(entity);
        log.info(Thread.currentThread().getStackTrace()[1] + " --- " + CurrentRequestHolder.getSerialNumber());
        return result;
    }

    public Pair<Long, Long> minMaxId(LocalDateTime shardingDate) {
        String tableName = mongoGeneratedKeyHandler().getTableName(shardingDate, getTypeClass());
        BusinessAcquisitionOdsEntity minIdObject = mongoTemplate().findOne(
                new Query().limit(1).with(new Sort(Sort.Direction.ASC, "id")),
                BusinessAcquisitionOdsEntity.class, tableName);
        BusinessAcquisitionOdsEntity maxIdObject = mongoTemplate().findOne(
                new Query().limit(1).with(new Sort(Sort.Direction.DESC, "id")),
                BusinessAcquisitionOdsEntity.class, tableName);

        return ImmutablePair.of(
                minIdObject != null ? minIdObject.getId() : -1,
                maxIdObject != null ? maxIdObject.getId() : -2
        );
    }

    public List<BusinessAcquisitionOdsEntity> findByIdRange(long rangeStart, long rangeEnd, LocalDateTime shardingDate) {
        String tableName = mongoGeneratedKeyHandler().getTableName(shardingDate, getTypeClass());
        Query query = Query.query(Criteria.where("id").gte(rangeStart).lte(rangeEnd));
        return mongoTemplate().find(query, this.getTypeClass(), tableName);
    }

    public List<BusinessAcquisitionOdsEntity> find(TrackPointEntity trackPoint, LocalDateTime shardingDate) {
        String tableName = mongoGeneratedKeyHandler().getTableName(shardingDate, getTypeClass());

        Criteria in = Criteria
                .where("param_page_id").is(trackPoint.getPageName())
                .and("platform").in("1", "22");

        Object[] eventType;
        if(EventTypeEnum.VIEW.maybe(trackPoint.getEventType())) {
            eventType = new String[] {"page"};
            in.and("param_key").in(eventType);
        }

        if(EventTypeEnum.CLICK.maybe(trackPoint.getEventType())) {
            eventType = new String[] {"click"};
            in.and("param_key").in(eventType);
        }

        if(StringUtils.isNotBlank(trackPoint.getMethodName())) {
            in.and("param_object_id").is(trackPoint.getMethodName());
        }

        Query query = Query.query(in);
        return mongoTemplate().find(query, this.getTypeClass(), tableName);
    }
}
