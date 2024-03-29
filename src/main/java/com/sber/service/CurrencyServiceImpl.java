package com.sber.service;

import com.sber.model.Currency;
import com.sber.model.CurrencyRequest;
import com.sber.model.ValCurs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceImpl implements ICurrencyService {
    Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    @Value("${currency.host}")
    private String currenciesUrl;
    @Value("${currency.everytimeconnect}")
    private boolean everytimeconnect;

    private RestTemplate restTemplace = new RestTemplate();
    private ValCurs valCurs = new ValCurs();
    private List<Currency> currencyList = new ArrayList<>();

    public List<Currency> getAllCurrencies() throws RestClientException {
        logger.info("Requesting to download the currency rate from external resource");
        valCurs = restTemplace.getForObject(currenciesUrl, ValCurs.class);
        return makeCurrencyList();
    }

    private List<Currency> makeCurrencyList() {
        List<Currency> currencyList = new ArrayList<>();
        if (valCurs.getDate() != null) {
            logger.info("The currency rate list has been received");
            currencyList = valCurs.getValute().stream()
                    .map(e -> {
                        Currency nc = new Currency(e.getNumCode(), e.getCharCode(), e.getName(), e.getValue());
                        return nc;
                    })
                    .collect(Collectors.toList());
        } else {
            logger.error("The currency rate list is empty");
        }
        this.currencyList = currencyList;
        return currencyList;
    }

    public String currencyConversion(CurrencyRequest currencyRequest) throws ParseException {
        Double amount = currencyRequest.getAmount();
        logger.info("Requesting for the currency conversion of " +
                currencyRequest.getCharCode() + "+ for amount of " + amount);

        if (valCurs.getDate() == null || everytimeconnect) {
            getAllCurrencies();
        }

        String rate = this.currencyList.stream()
                .filter(c -> c.getCharCode().equals(currencyRequest.getCharCode()))
                .findFirst()
                .map(c -> c.getValue().replace(",", "."))
                .orElse(null);
        String result = String.valueOf(Double.parseDouble(rate) * amount).replace(".", ",");
        logger.info("The rate is " + rate + " * " + amount + " = the calculated result = " + result);
        return result;

    }

    public ValCurs getValCurs() {
        return valCurs;
    }
}
