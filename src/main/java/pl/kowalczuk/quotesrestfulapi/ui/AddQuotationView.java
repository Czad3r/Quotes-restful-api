package pl.kowalczuk.quotesrestfulapi.ui;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "add")
public class AddQuotationView extends VerticalLayout {
    Label labelText = new Label("Cytat");
    TextField text = new TextField("Enter this");
    Label labelAuthor = new Label("Autor");
    TextField author = new TextField("Enter this");
    Label labelSource = new Label("Źródło");
    TextField source = new TextField("Enter this");
    //Button addBtn = new Button("Dodaj cytat", event -> Notification.show("Clicked!"));

    public AddQuotationView() {
        add(labelText, text,
                labelAuthor, author,
                labelSource, source

                //,addBtn
        );
        setSizeFull();
    }
}
