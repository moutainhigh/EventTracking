package com.apoem.mmxx.eventtracking.infrastructure.po.ro;

import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Affix;
import com.apoem.mmxx.eventtracking.infrastructure.dao.support.Sharding;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: InfoAgentH5Ro </p>
 * <p>Description: H5资讯经纪人PV、UV统计中间结果 </p>
 * <p>Date: 2020/11/19 9:07 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author somebody
 * @version v1.0
 */
@Data
@ToString
@Sharding(fix = Affix.FORMAT)
@Document("et_{}_info_agent_h5")
public class InfoAgentH5Ro {

    private Key id;
    private Value value;

    @Data
    public static class Key {
        private String agentId;
        private String city;
    }

    @Data
    public static class Value {
        private Long pageView;
        private Long uniqueVisitor;
    }
}
