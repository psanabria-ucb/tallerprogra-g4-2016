package bo.edu.ucbcba.prestamix.group4.model;

import javax.persistence.*;

@Entity
public class Customer
{
    @Id
    private int Ci;

    @Column(length = 100)
    private String firtsName;

    @Column(length = 100)
    private String lastNameF;

    @Column(length = 100)
    private String lastNameM;

    @Lob
    @Column(length = 100)
    private String address;
    private int numberPhone;

    public int getCi() {
        return Ci;
    }

    public void setCi(int ci) {
        Ci = ci;
    }

    public String getFirtsName() {
        return firtsName;
    }

    public void setFirtsName(String firtsName) {
        this.firtsName = firtsName;
    }

    public String getLastNameF() {
        return lastNameF;
    }

    public void setLastNameF(String lastNameF) {
        this.lastNameF = lastNameF;
    }

    public String getLastNameM() {
        return lastNameM;
    }

    public void setLastNameM(String lastNameM) {
        this.lastNameM = lastNameM;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(int numberPhone) {
        this.numberPhone = numberPhone;
    }
}
