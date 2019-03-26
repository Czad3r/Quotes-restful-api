package pl.kowalczuk.quotesrestfulapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kowalczuk.quotesrestfulapi.exception.ResourceNotFoundException;
import pl.kowalczuk.quotesrestfulapi.model.Quotation;
import pl.kowalczuk.quotesrestfulapi.repository.QuotationRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quotations")
public class QuotationController {

    @Autowired
    private QuotationRepository quotationRepository;

    @GetMapping("/getAll")
    public List<Quotation> getAllQuotations() {
        return quotationRepository.findAll();
    }

    @GetMapping("/getAll/{id}")
    public ResponseEntity<Quotation> getQuotationById(@PathVariable(value = "id") Long quotationId)
            throws ResourceNotFoundException {
        Quotation quotation = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new ResourceNotFoundException("Quotation not found for this id :: " + quotationId));
        return ResponseEntity.ok().body(quotation);
    }

    @GetMapping("/getRandom")
    public ResponseEntity<Quotation> getQuotationRandom()
            throws ResourceNotFoundException {
        long leftLimit = 1L;
        long rightLimit = quotationRepository.count();
        long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));

        Quotation quotation = quotationRepository.findById(generatedLong)
                .orElseThrow(() -> new ResourceNotFoundException("Quotation not found for this id :: " + generatedLong));
        return ResponseEntity.ok().body(quotation);
    }

    @PostMapping("/postQuotation")
    public Quotation createQuotation(@Valid @RequestBody Quotation quotation) {
        return quotationRepository.save(quotation);
    }

    @PutMapping("/postQuotation/{id}")
    public ResponseEntity<Quotation> updateQuotation(@PathVariable(value = "id") Long quotationId,
                                                   @Valid @RequestBody Quotation quotationDetails) throws ResourceNotFoundException {
        Quotation quotation = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new ResourceNotFoundException("Quotation not found for this id :: " + quotationId));

        quotation.setAuthor(quotationDetails.getAuthor());
        quotation.setSource(quotationDetails.getSource());
        quotation.setText(quotationDetails.getText());
        final Quotation updatedQuotation = quotationRepository.save(quotation);
        return ResponseEntity.ok(updatedQuotation);
    }

    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long quotationId)
            throws ResourceNotFoundException {
        Quotation quotation = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new ResourceNotFoundException("Quotation not found for this id :: " + quotationId));

        quotationRepository.delete(quotation);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
