package com.nice.wfm.service;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.nice.wfm.domain.Message;
import org.apache.http.HttpStatus;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created on 8/23/18.
 * Author: filmon
 * Apple Inc.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Rule
    public PactProviderRuleMk2 provider = new PactProviderRuleMk2("messagesService", "localhost", 8080, this);

    @Pact(consumer = "messagesClient")
    public RequestResponsePact createPact(PactDslWithProvider builder){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        PactDslJsonBody messageJsonBody = new PactDslJsonBody()
                .integerType("id", 1L)
                .stringType("message", "Hello World")
//                .date("created", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", new Date())
                .stringType("author", "koushik")
                .asBody();

        return builder
                .given("There is a message with id: 1")
                .uponReceiving("A request for message with id: 1")
                    .path("/messenger/webapi/messages/1")
                    .method("GET")
                .willRespondWith()
                    .status(HttpStatus.SC_OK)
                    .headers(headers)
                    .body(messageJsonBody).toPact();
    }

    @Test
    @PactVerification
    public void testGetMessages(){
        System.setProperty("pact.rootDir", "/Users/filmon/Documents/Pact/client/pacts");
        Message message = messageService.getMessage(1L);
        assertThat(message, notNullValue());
        assertThat(message.getAuthor(), equalTo("koushik"));
    }

}