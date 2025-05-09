package com.rbikiteev.user_subscriptions.service;

import com.rbikiteev.user_subscriptions.entity.SubscriptionEntity;
import com.rbikiteev.user_subscriptions.entity.UserEntity;
import com.rbikiteev.user_subscriptions.exceptions.SubscriptionAlreadyExistsException;
import com.rbikiteev.user_subscriptions.exceptions.UserNotFoundException;
import com.rbikiteev.user_subscriptions.repository.SubscriptionRepository;
import com.rbikiteev.user_subscriptions.repository.UserRepository;
import com.rbikiteev.user_subscriptions.view.SubscriptionCountView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscriptionEntity addSubscription(UUID userId, String serviceName) {
        if (!subscriptionRepository.findByUserIdAndServiceName(userId, serviceName).isEmpty()) {
            log.error(String.format("Subscription %s for user %s already exists", serviceName, userId));
            throw new SubscriptionAlreadyExistsException(String.format("Subscription %s for user %s already exists", serviceName, userId));
        }
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> {
                   log.error(String.format("User %s not found", userId));
                   return new UserNotFoundException(String.format("User %s not found", userId));
                });
        SubscriptionEntity subscription = new SubscriptionEntity();
        UUID subscriptionId = generateUUIDFromServiceNameAndUserId(serviceName, userId);
        subscription.setId(subscriptionId);
        subscription.setServiceName(serviceName);
        subscription.setUserId(user.getId());
        log.info(String.format("Subscription %s was added to user %s", serviceName, userId));
        return subscriptionRepository.save(subscription);
    }

    public List<SubscriptionEntity> getSubscriptions(UUID userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    private static UUID generateUUIDFromServiceNameAndUserId(String serviceName, UUID userId) {
        String combined = serviceName + userId.toString();
        return UUID.nameUUIDFromBytes(combined.getBytes(StandardCharsets.UTF_8));
    }

    public void deleteSubscription(UUID userId, UUID subId) {
        SubscriptionEntity sub = subscriptionRepository.findById(subId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", userId)));
        if (!sub.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Subscription does not belong to user");
        }
        subscriptionRepository.delete(sub);
        log.info(String.format("Subscription with id %s was deleted", subId));
    }

    public List<SubscriptionCountView> getTopSubscriptions() {
        return subscriptionRepository.findTop3Subscriptions();
    }
}
