package pl.kowalczuk.quotesrestfulapi.repository;

import pl.kowalczuk.quotesrestfulapi.model.Quotation;

import java.util.List;

public interface QuotationRepositoryCustom {
    List<Quotation> findByFilter(String filterText);
}
