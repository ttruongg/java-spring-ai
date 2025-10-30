package com.example.tool_calling.service;

import com.example.tool_calling.dto.request.HelpDeskTicketRequest;
import com.example.tool_calling.dto.response.HelpDeskTicketDTO;
import com.example.tool_calling.entity.HelpDeskTicket;
import com.example.tool_calling.repository.HelpDeskTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HelpDeskTicketService {

    private final HelpDeskTicketRepository helpDeskTicketRepository;

    public HelpDeskTicketDTO createHelpDeskTicket(HelpDeskTicketRequest ticketInput, String username) {

        HelpDeskTicket ticket = initializeHelpDeskTicket(ticketInput, username);
        HelpDeskTicket savedTicket = helpDeskTicketRepository.save(ticket);
        return mapToHelpDeskTicketDTO(savedTicket);

    }

    private HelpDeskTicket initializeHelpDeskTicket(HelpDeskTicketRequest ticketInput, String username) {
        return HelpDeskTicket.builder()
                .username(username)
                .issue(ticketInput.issue())
                .createdAt(LocalDateTime.now())
                .status("OPEN")
                .eta(LocalDateTime.now().plusDays(7))
                .build();
    }

    private HelpDeskTicketDTO mapToHelpDeskTicketDTO(HelpDeskTicket ticket) {
        return HelpDeskTicketDTO.builder()
                .id(ticket.getId())
                .username(ticket.getUsername())
                .issue(ticket.getIssue())
                .status(ticket.getStatus())
                .createdAt(ticket.getCreatedAt())
                .eta(ticket.getEta())
                .build();
    }

    public List<HelpDeskTicketDTO> getTicketsByUsername(String username) {
        List<HelpDeskTicket> tickets = helpDeskTicketRepository.findByUsername(username);
        return tickets.stream().map(this::mapToHelpDeskTicketDTO).toList();
    }
}
