package com.hronosf.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "timestamps")
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class EntityWithTimeStamps extends BaseEntity {

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
