package com.sber.service;

import com.sber.model.Currency;
import com.sber.model.CurrencyRequest;
import com.sber.model.ValCurs;

import java.text.ParseException;
import java.util.List;

public interface ICurrencyService {
    List<Currency> getAllCurrencies();
    String currencyConversion(CurrencyRequest currencyRequest) throws ParseException;
    ValCurs getValCurs();
}
