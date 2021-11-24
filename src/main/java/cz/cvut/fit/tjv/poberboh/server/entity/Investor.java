package cz.cvut.fit.tjv.poberboh.server.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "investor")
public class Investor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstname;
    private String lastname;

    @ManyToMany
    @JoinTable(
            name = "invested",
            joinColumns = @JoinColumn(name = "investor_id"),
            inverseJoinColumns = @JoinColumn(name = "startup_id"))
    private List<Startup> invested;

    public Investor() {
    }

    public Investor(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Investor(int id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Startup> getInvested() {
        return invested;
    }

    public void setInvested(List<Startup> invested) {
        this.invested = invested;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Investor investor = (Investor) o;
        return Objects.equals(id, investor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Investor{" +
                "id=" + id +
                '}';
    }
}
