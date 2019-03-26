package pl.kowalczuk.quotesrestfulapi.model;

import javax.persistence.*;

@Entity
@Table(name = "quotesTable")
public class Quotation {

    private long id;
    private String author;
    private String source;
    private String text;

    public Quotation() {
    }

    public Quotation(String author, String source, String text) {
        this.author = author;
        this.source = source;
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "author", nullable = false)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column(name = "source", nullable = false)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    @Column(name = "text", nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Quatation [id=" + id + ", author=" + author + ", source=" + source + ", text=" + text
                + "]";
    }
}
