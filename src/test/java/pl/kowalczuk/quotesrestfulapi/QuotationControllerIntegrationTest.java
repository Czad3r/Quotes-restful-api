package pl.kowalczuk.quotesrestfulapi;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import pl.kowalczuk.quotesrestfulapi.model.Quotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuotesRestfulApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuotationControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetAllQuotations() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/quotations/getAll",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetQuotationById() {
        Quotation quotation = restTemplate.getForObject(getRootUrl() + "/quotations/getAll/1", Quotation.class);
        assertNotNull(quotation);
    }

    @Test
    public void testCreateQuotation() {
        Quotation quotation = new Quotation();
        quotation.setId(5);
        quotation.setAuthor("Anżej Sapkowski");
        quotation.setSource("Własne");
        quotation.setText("Na pohybel .....");
        ResponseEntity<Quotation> postResponse = restTemplate.postForEntity(getRootUrl() + "/postQuotation", quotation, Quotation.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateQuotation() {
        int id = 1;
        Quotation quotation = restTemplate.getForObject(getRootUrl() + "/quotation/getAll/" + id, Quotation.class);
        quotation.setAuthor("Grzesio Sajkowski");
        restTemplate.put(getRootUrl() + "/postQuotation/" + id, quotation);
        Quotation updatedQuotation = restTemplate.getForObject(getRootUrl() + "/quotation/getAll/" + id, Quotation.class);
        assertNotNull(updatedQuotation);
        assertNotEquals(quotation,updatedQuotation);
    }

    @Test
    public void testDeleteQuotation() {
        int id = 2;
        Quotation quotation = restTemplate.getForObject(getRootUrl() + "/quotation/getAll/" + id, Quotation.class);
        assertNotNull(quotation);
        restTemplate.delete(getRootUrl() + "/deleteQuotation/" + id);
        try {
            quotation = restTemplate.getForObject(getRootUrl() + "/quotation/getAll/" + id, Quotation.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
