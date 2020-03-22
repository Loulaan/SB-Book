package dto.payload.request;

import lombok.Data;

@Data
public class SaveBookRequestDTO {

    private String title;
    private String description;
    private String price;
    private String author;
    private String publishingHouse;
}
