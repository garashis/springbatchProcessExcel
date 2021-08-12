package org.springframework.batch.extensions.excel.dto;

public class StudentDTO {

//    private Integer id;
    private String emailAddress;
    private String name;
    private String purchasedPackage;

    public StudentDTO() {}

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    @Override
    public String toString() {
        return "StudentDTO{" +
//                "id=" + id +
                ", emailAddress='" + emailAddress + '\'' +
                ", name='" + name + '\'' +
                ", purchasedPackage='" + purchasedPackage + '\'' +
                '}';
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
