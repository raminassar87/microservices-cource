package com.in28minutes.microservices.currencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExhangeServiceProxy proxy;

    @GetMapping("currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyhConversionBean convertCurrency(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity) {

        Map<String,String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);

        ResponseEntity<CurrencyhConversionBean> responseEntity = new RestTemplate().
                getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyhConversionBean.class,
                uriVariables);

        CurrencyhConversionBean response = responseEntity.getBody();

        return new CurrencyhConversionBean(response.getId(),response.getFrom(),response.getTo(),BigDecimal.ONE,
                quantity,quantity.multiply(response.getConversionMultiple()),response.getPort());
    }

    @GetMapping("currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyhConversionBean convertCurrencyFeign(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity) {

        System.out.println("******** from : "+ from);
        System.out.println("******** to : "+ to);

        CurrencyhConversionBean response = proxy.retrieveExchangeValue(from,to);

        return new CurrencyhConversionBean(response.getId(),response.getFrom(),response.getTo(),BigDecimal.ONE,
                quantity,quantity.multiply(response.getConversionMultiple()),response.getPort());
    }

}