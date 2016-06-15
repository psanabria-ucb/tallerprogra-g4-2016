package bo.edu.ucbcba.prestamix.group4.model;

import javax.persistence.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int numberPay;

    private int interest;

    private int amount;

    private String date;

    public int getNumberPay() {
        return numberPay;
    }

    public void setNumberPay(int numberPay) {
        this.numberPay = numberPay;
    }

    public int getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
