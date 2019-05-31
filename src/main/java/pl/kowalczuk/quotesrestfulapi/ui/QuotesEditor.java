package pl.kowalczuk.quotesrestfulapi.ui;


import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kowalczuk.quotesrestfulapi.model.Quotation;
import pl.kowalczuk.quotesrestfulapi.repository.QuotationRepository;


@SpringComponent
@UIScope
public class QuotesEditor extends VerticalLayout implements KeyNotifier {

    @Autowired
    private QuotationRepository repository;
    private Quotation quotation;

    TextField text = new TextField("Quotation");
    TextField author = new TextField("Author");
    TextField source = new TextField("Source");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());

    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
    Binder<Quotation> binder = new Binder<>(Quotation.class);
    private ChangeHandler changeHandler;

    public QuotesEditor() {
        add(text, author, source);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editQuotation(quotation));
        setVisible(false);
    }

    void delete() {
        repository.delete(quotation);
        changeHandler.onChange();
    }

    void save() {
        repository.save(quotation);
        changeHandler.onChange();
    }

    public final void editQuotation(Quotation q) {
        if (q == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = q.getId() != 0;
        if (persisted) {
            // Find fresh entity for editing
            quotation = repository.findById(q.getId()).get();
        }
        else {
            quotation = q;
        }
        cancel.setVisible(persisted);

        // Bind quotation properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(quotation);

        setVisible(true);

        // Focus first name initially
        text.focus();
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }

    public interface ChangeHandler {
        void onChange();
    }
}
