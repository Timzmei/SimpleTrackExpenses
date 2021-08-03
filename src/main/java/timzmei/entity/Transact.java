package timzmei.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transact implements Serializable, Comparable<Transact>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "title")
    private String title;

    public Transact() {
    }

    public Transact(Double amount, LocalDate date, String title) {
        this.amount = amount;
        this.date = date;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int compareTo(Transact other) {
        int diff = date.compareTo(other.getDate());
        if (diff == 0)
            return (int) (other.getAmount() - amount);
        return diff;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Transact other = (Transact) obj;
        if (date.equals(other.getDate()))
            if (amount == other.getAmount())
                if (title.equalsIgnoreCase(other.getTitle()))
                    return true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = hash * 7 + (int) amount * Objects.hashCode(date);
        return hash;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Amount: " + amount + "/ ";
        s += "Date:   " + date + "/ ";
        s += "Title:  " + title;
        return s;
    }
}
