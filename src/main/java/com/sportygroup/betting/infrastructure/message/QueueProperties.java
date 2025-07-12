package com.sportygroup.betting.infrastructure.message;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.messaging.formulaone.bets")
public record QueueProperties(@NotEmpty @Min(3) @Max(100) String queue) { }
