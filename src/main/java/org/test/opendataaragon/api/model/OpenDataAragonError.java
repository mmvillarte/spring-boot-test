package org.test.opendataaragon.api.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OpenDataAragonError {
    private int errorCode;
    private String errorMsg;
    private LocalDateTime errorTimestamp;
}
