package com.rbikiteev.user_subscriptions.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Table(name = "subscriptions")
public class SubscriptionEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "user_id")
    private UUID userId;
}
