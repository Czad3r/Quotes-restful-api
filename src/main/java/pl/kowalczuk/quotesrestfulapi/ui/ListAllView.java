package pl.kowalczuk.quotesrestfulapi.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import pl.kowalczuk.quotesrestfulapi.model.Quotation;
import pl.kowalczuk.quotesrestfulapi.repository.QuotationRepository;

@Route(value = "listAll")
public class ListAllView extends VerticalLayout {

    private final QuotationRepository repository;

    private final QuotesEditor editor;

    final Grid<Quotation> grid;
    final TextField filter;
    final Button addNewBtn;

    @Autowired
    public ListAllView(QuotationRepository repository, QuotesEditor editor) {
        this.repository = repository;
        this.editor = editor;
        this.grid = new Grid<>(Quotation.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New quotation", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, this.editor);

        grid.setHeight("200px");
        grid.setColumns("id", "Quote", "Author", "Source");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        grid.asSingleSelect().addValueChangeListener(e -> {
            this.editor.editQuotation(e.getValue());
        });

        filter.setPlaceholder("Filter by ...");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listQuotes(e.getValue()));

        addNewBtn.addClickListener(e -> this.editor.editQuotation(new Quotation("", "", "")));

        this.editor.setChangeHandler(() -> {
            this.editor.setVisible(false);
            listQuotes(filter.getValue());
        });

        listQuotes(null);
    }

    void listQuotes(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repository.findAll());
        } else {
            grid.setItems(repository.findByFilter(filterText));
        }
    }
}
