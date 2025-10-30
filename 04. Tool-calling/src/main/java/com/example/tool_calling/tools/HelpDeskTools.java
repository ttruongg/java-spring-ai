package com.example.tool_calling.tools;

import com.example.tool_calling.dto.request.HelpDeskTicketRequest;
import com.example.tool_calling.dto.response.HelpDeskTicketDTO;
import com.example.tool_calling.service.HelpDeskTicketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HelpDeskTools {

    private final Logger logger = LoggerFactory.getLogger(HelpDeskTools.class);
    private final HelpDeskTicketService helpDeskTicketService;

    @Tool(name = "createTicket", description = "Create a Support ticket")
    public String createTicket(@ToolParam(description = "Details to create a Support ticket")
                               HelpDeskTicketRequest ticketRequest, ToolContext toolContext) {

        String username = (String) toolContext.getContext().get("username");
        HelpDeskTicketDTO savedTicket = helpDeskTicketService.createHelpDeskTicket(ticketRequest, username);
        return "Ticket created with ID: " + savedTicket.getId();

    }

    @Tool(name = "getTicketsByUsername", description = "Fetch Support tickets for a given username")
    public List<HelpDeskTicketDTO> getTicketsByUsername(ToolContext toolContext) {
        String username = (String) toolContext.getContext().get("username");
        return helpDeskTicketService.getTicketsByUsername(username);
    }

}
