package com.hronosf.model.entity;

import com.hronosf.model.BaseEntity;
import com.hronosf.model.enums.TokenStatus;
import com.hronosf.model.enums.TokenTarget;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "verification_tokens")
@EqualsAndHashCode(callSuper = true)
public class Token extends BaseEntity {

    @Column(name = "token")
    private String tokenString;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_status")
    private TokenStatus tokenStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_target")
    private TokenTarget tokenTarget;

    @JoinColumn(name = "id")
    @OneToOne(fetch = FetchType.EAGER)
    private Person person;
}
