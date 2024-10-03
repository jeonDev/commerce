package com.commerce.core.event;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class LocalEventDto {
    private final EventTopic topic;
    private final Object data;
}
