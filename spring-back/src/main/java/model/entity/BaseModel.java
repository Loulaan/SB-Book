package model.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_timestamp")
    private Date createdTimestamp;

    @LastModifiedDate
    @Column(name = "updated_timestamp")
    private Date updatedTimestamp;

    @LastModifiedDate
    @Column(name = "deleted_timestamp")
    private Date deletedTimestamp;
}
