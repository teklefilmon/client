package com.nice.wfm.controller;

import com.nice.wfm.domain.Message;
import com.nice.wfm.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Created on 8/23/18.
 * Author: filmon
 * Apple Inc.
 */

@RestController
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService){
        Objects.requireNonNull(messageService);
        this.messageService = messageService;
    }

    @GetMapping("/client/messages/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable("id") long id){
        Message message = messageService.getMessage(id);
        if(Objects.nonNull(message))
            return ResponseEntity.ok(message);

        return ResponseEntity.noContent().build();
    }
}
