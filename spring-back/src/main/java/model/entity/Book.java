package model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "books")
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseModel {

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private String price;

    @Column(name = "author")
    private String author;

    @Column(name = "publishing_house")
    private String publishingHouse;
}
