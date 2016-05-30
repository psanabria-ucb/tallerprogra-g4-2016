package bo.edu.ucbcba.prestamix.group4.model;

import javax.persistence.*;

@Entity
public class Pledge
{
    @Id
    private String cod;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String type;

    @Lob
    @Column(length = 500)
    private String description;

    private String location;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return String.format("%s %s", cod, name);
    }
}
