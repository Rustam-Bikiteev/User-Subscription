package com.rbikiteev.user_subscriptions.repository;

import com.rbikiteev.user_subscriptions.entity.SubscriptionEntity;
import com.rbikiteev.user_subscriptions.view.SubscriptionCountView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, UUID> {

    List<SubscriptionEntity> findByUserId(UUID userId);

    List<SubscriptionEntity> findByUserIdAndServiceName(UUID userId, String serviceName);

    @Query(value = """
                SELECT service_name as serviceName,
                count(DISTINCT user_id) as subscriptionCount
                FROM subscriptions
                GROUP BY service_name
                ORDER BY COUNT(DISTINCT user_id) DESC
                LIMIT 3
            """, nativeQuery = true)
    List<SubscriptionCountView> findTop3Subscriptions();

}
