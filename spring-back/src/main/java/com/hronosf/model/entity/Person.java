package com.hronosf.model.entity;

import com.hronosf.model.EntityWithTimeStamps;
import com.hronosf.model.enums.AccountStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "person")
@EqualsAndHashCode(callSuper = true)
public class Person extends EntityWithTimeStamps {

    @Column(name = "user_name", unique = true)
    protected String userName;

    @Column(name = "password")
    protected String password;

    @Column(name = "is_activated", nullable = false)
    protected boolean isActivated;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    protected AccountStatus accountStatus;

    @Column(name = "last_login")
    protected Date lastLoginTime;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "persons_to_roles",
            joinColumns = {@JoinColumn(name = "person_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    protected List<Roles> roles;

    public void setRoles(Roles role) {
        this.roles = Collections.singletonList(role);
    }
}
