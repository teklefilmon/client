package com.nice.wfm.service;

import com.nice.wfm.domain.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * Created on 8/23/18.
 * Author: filmon
 * Apple Inc.
 */

@Service
public class MessageService {

    private RestTemplate restTemplate;

    public MessageService(RestTemplate restTemplate){
        Objects.requireNonNull(restTemplate);
        this.restTemplate = restTemplate;
    }

    public Message getMessage(long id) {
        return restTemplate.getForObject("http://localhost:8080/messenger/webapi/messages/" + id, Message.class);
    }
}
