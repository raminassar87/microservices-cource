package com.in28minutes.microservices.currencyconversionservice;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="currency-exchange-service",url="localhost:8000")
//@FeignClient(name="currency-exchange-service") // no need for URL in case of using Ribbon
@FeignClient(name="netflix-zuul-api-gateway-server") // ZUUl Proxy
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExhangeServiceProxy {
    @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    CurrencyhConversionBean retrieveExchangeValue
            (@PathVariable(value="from") String from,@PathVariable(value="to") String to);
}
