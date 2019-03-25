package pl.kowalczuk.quotesrestfulapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import pl.kowalczuk.quotesrestfulapi.model.Quotation;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {
}
