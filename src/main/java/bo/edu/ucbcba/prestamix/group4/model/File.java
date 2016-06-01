package bo.edu.ucbcba.prestamix.group4.model;

import javax.persistence.*;

@Entity
public class File {
    @Id
    private int id;

    private String nameCustomer;

    private String codPledge;

    private int amount;

    private String type;

    private String date;

    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getCodPledge() {
        return codPledge;
    }

    public void setCodPledge(String codPledge) {
        this.codPledge = codPledge;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
