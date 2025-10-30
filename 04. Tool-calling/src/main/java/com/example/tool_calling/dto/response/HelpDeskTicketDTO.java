package com.example.tool_calling.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class HelpDeskTicketDTO {
    private Long id;
    private String username;
    private String issue;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime eta;
}
