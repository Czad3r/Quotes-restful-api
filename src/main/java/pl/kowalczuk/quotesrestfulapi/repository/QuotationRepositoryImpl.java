package pl.kowalczuk.quotesrestfulapi.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.kowalczuk.quotesrestfulapi.model.Quotation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class QuotationRepositoryImpl implements QuotationRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Quotation> findByFilter(String filterText) {
        Query query = entityManager.createNativeQuery("SELECT * FROM quotes_table q " +
                "WHERE q.author LIKE ? OR q.source LIKE ? OR q.text LIKE ?", Quotation.class);
        query.setParameter(1,"%"+ filterText + "%");
        query.setParameter(2,"%"+ filterText + "%");
        query.setParameter(3,"%"+ filterText + "%");
        return query.getResultList();
    }
}
