package com.sber.controller;

import com.sber.model.Currency;
import com.sber.model.CurrencyRequest;
import com.sber.service.ICurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    ICurrencyService iCurrencyService;

    @GetMapping
    public List<Currency> getCurrencyCharCode() {
        return iCurrencyService.getAllCurrencies();
    }

    @PostMapping
    public String currencyConversion(@RequestBody CurrencyRequest currencyRequest) throws ParseException {
        return iCurrencyService.currencyConversion(currencyRequest);
    }
}
