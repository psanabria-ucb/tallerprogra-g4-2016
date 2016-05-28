package bo.edu.ucbcba.prestamix.group4.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Pawn
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int ciCustomer;

    @Column(length = 100)
    private String codPledge;

    private int amount;

    @Column(length = 10)
    private String type;

    private Date date;

    @Column(length = 10)
    private String status;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Pledge pledge;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodPledge() {
        return codPledge;
    }

    public void setCodPledge(String codPledge) {
        this.codPledge = codPledge;
    }

    public int getCiCustomer() {
        return ciCustomer;
    }

    public void setCiCustomer(int ciCustomer) {
        this.ciCustomer = ciCustomer;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Pledge getPledge() {
        return pledge;
    }

    public void setPledge(Pledge pledge) {
        this.pledge = pledge;
    }
}
