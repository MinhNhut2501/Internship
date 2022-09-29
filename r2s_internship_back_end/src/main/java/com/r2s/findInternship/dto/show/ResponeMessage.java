package com.r2s.findInternship.dto.show;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponeMessage {
    private int httpCode;
    private String message;
}
