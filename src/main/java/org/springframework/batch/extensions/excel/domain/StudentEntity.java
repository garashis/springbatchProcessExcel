package org.springframework.batch.extensions.excel.domain;

import javax.persistence.*;

@Entity
public class StudentEntity {
    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String emailAddress;
    private String name;
    private String purchasedPackage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurchasedPackage() {
        return purchasedPackage;
    }

    public void setPurchasedPackage(String purchasedPackage) {
        this.purchasedPackage = purchasedPackage;
    }


}
