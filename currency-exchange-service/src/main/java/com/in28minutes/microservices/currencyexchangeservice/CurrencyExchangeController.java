package com.in28minutes.microservices.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private ExchangeValueRepository repository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable String from,@PathVariable String to) {
        ExchangeValue value = repository.findByFromAndTo(from,to);
        //ExchangeValue value = new ExchangeValue(1000L,from,to, BigDecimal.valueOf(65));
        value.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        return value;
    }
}