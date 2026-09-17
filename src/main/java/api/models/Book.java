package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    private String isbn;
    private String title;
    private String subTitle;
    private String author;
    private String publishDate;
    private String publisher;
    private int pages;
    private String description;
    private String website;
}
