package com.commerce.core.event.type.local;

import com.commerce.core.event.type.EventTopic;
import lombok.Builder;

@Builder
public record QueueData(
        EventTopic topic,
        Object data
) {
}
