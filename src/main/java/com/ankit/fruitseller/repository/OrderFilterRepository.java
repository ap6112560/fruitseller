package com.ankit.fruitseller.repository;

import org.hibernate.query.NativeQuery;
import org.hibernate.type.PostgresUUIDType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Repository
public class OrderFilterRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<UUID> getOrderIdsByFilters(UUID orderId, String shipMethod, String orderStatus, int pageSize, int pageNo) {
        ArrayList<String> whereClauses = new ArrayList<>();
        Map<String, Object> parameterMap = new HashMap<>();
        String query = "select o.order_id from orders o ";

        if (orderId != null) {
            whereClauses.add("o.order_id = :orderId");
            parameterMap.put("orderId", orderId);
        }

        if (orderStatus != null) {
            whereClauses.add("o.status = :oStatus");
            parameterMap.put("oStatus", orderStatus);
        }

        if (shipMethod != null) {
            query = query.concat("inner join shipment s on o.order_id = s.order_id ");
            whereClauses.add("s.shipment_method = :sMethod");
            parameterMap.put("sMethod", shipMethod);
        }

        if (!whereClauses.isEmpty()) {
            query = query.concat("where ") + String.join(" and ", whereClauses);
        }

        query = query.concat(" order by o.order_date asc limit :limit offset :offset");
        parameterMap.put("limit", pageSize);
        parameterMap.put("offset", (pageNo - 1) * pageSize);

        Query nativeQuery = entityManager.createNativeQuery(query);
        parameterMap.forEach(nativeQuery::setParameter);
        nativeQuery.unwrap(NativeQuery.class).addScalar("order_id", PostgresUUIDType.INSTANCE);

        return (List<UUID>) nativeQuery.getResultList();
    }
}
