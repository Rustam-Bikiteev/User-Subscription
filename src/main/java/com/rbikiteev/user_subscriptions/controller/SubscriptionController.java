package com.rbikiteev.user_subscriptions.controller;

import com.rbikiteev.user_subscriptions.entity.SubscriptionEntity;
import com.rbikiteev.user_subscriptions.service.SubscriptionService;
import com.rbikiteev.user_subscriptions.view.SubscriptionCountView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/{id}")
    public ResponseEntity<SubscriptionEntity> add(@PathVariable UUID id, @RequestParam String serviceName) {
        return ResponseEntity.ok(subscriptionService.addSubscription(id, serviceName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<SubscriptionEntity>> list(@PathVariable UUID id) {
        return ResponseEntity.ok(subscriptionService.getSubscriptions(id));
    }

    @DeleteMapping("/{subId}/user/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @PathVariable UUID subId) {
        subscriptionService.deleteSubscription(id, subId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/top3")
    public ResponseEntity<List<SubscriptionCountView>> getTop() {
        return ResponseEntity.ok(subscriptionService.getTopSubscriptions());
    }

    /**
     * Только для тестов
     */
    @PostMapping("/init")
    public void init() {
        subscriptionService.addSubscription(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Netflix");
        subscriptionService.addSubscription(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Youtube");
        subscriptionService.addSubscription(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Google");
        subscriptionService.addSubscription(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Amazone");
        subscriptionService.addSubscription(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Disney");
        subscriptionService.addSubscription(UUID.fromString("11111111-1111-1111-1111-111111111111"), "Spotify");
        subscriptionService.addSubscription(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Amazone");
        subscriptionService.addSubscription(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Disney");
        subscriptionService.addSubscription(UUID.fromString("22222222-2222-2222-2222-222222222222"), "Spotify");
        subscriptionService.addSubscription(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Amazone");
        subscriptionService.addSubscription(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Disney");
        subscriptionService.addSubscription(UUID.fromString("33333333-3333-3333-3333-333333333333"), "Spotify");
    }

}

