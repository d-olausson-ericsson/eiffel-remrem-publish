package com.ericsson.eiffel.remrem.publish.controller;

import com.ericsson.eiffel.remrem.publish.helper.ResponseHelper;
import com.ericsson.eiffel.remrem.publish.service.MessageService;
import com.ericsson.eiffel.remrem.publish.service.SendResult;
import com.google.gson.JsonElement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j @RestController @RequestMapping("/producer") public class ProducerController {

    @Autowired @Qualifier("messageServiceRMQImpl") MessageService messageService;
    @Autowired @Qualifier("responseHelper") ResponseHelper responseHelper;

    @RequestMapping(value = "/msg", method = RequestMethod.POST) @ResponseBody
    public List<String> send(@RequestParam(value = "rk", required = true) String routingKey,
        @RequestBody JsonElement body) {
        log.debug("routingKey: " + routingKey);
        log.debug("body: " + body);

        List<SendResult> results = messageService.send(routingKey, body);
        return responseHelper.convert(results);
    }
}
