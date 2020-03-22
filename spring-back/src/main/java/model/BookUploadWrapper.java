package model;

import lombok.Data;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

@Data
public class BookUploadWrapper {

    private String title;
    private String description;
    private String price;
    private String author;
    private String publishingHouse;
    private List<CommonsMultipartFile> files;

}
