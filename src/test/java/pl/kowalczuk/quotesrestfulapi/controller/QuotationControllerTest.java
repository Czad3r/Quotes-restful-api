package pl.kowalczuk.quotesrestfulapi.controller;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import pl.kowalczuk.quotesrestfulapi.model.Quotation;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class QuotationControllerTest {

    @Test
    public void should_Get_ListOfQuotations() {
        QuotationController quotationController = mock(QuotationController.class);
        given(quotationController.getAllQuotations()).willReturn(prepareMockData());

        List<Quotation> list = quotationController.getAllQuotations();

        Assert.assertThat(list, Matchers.hasSize(3));
    }

    @Test
    public void should_Add_Quotation() {
        QuotationController quotationController = mock(QuotationController.class);
        Quotation quotation = new Quotation();
        quotation.setText("TEST test");
        given(quotationController.createQuotation(Mockito.any(Quotation.class))).willReturn(quotation);

        Quotation quotation1 = quotationController.createQuotation(new Quotation());

        Assert.assertEquals(quotation1.getText(), "TEST test");
    }

    private List<Quotation> prepareMockData() {
        ArrayList<Quotation> list = new ArrayList<Quotation>();
        list.add(new Quotation());
        list.add(new Quotation());
        list.add(new Quotation());
        return list;
    }
}