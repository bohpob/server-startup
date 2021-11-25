package cz.cvut.fit.tjv.poberboh.server.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "startup")
public class Startup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer investment;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany(mappedBy = "invested")
    private List<Investor> investors;

    public Startup(Integer id, String name, Integer investment) {
        this.id = id;
        this.name = name;
        this.investment = investment;
    }

    public Startup(String name, Integer investment, Owner owner) {
        this.name = name;
        this.investment = investment;
        this.owner = owner;
    }

    public Startup(String name, Integer investment) {
        this.name = name;
        this.investment = investment;
    }

    public List<Investor> getInvestors() {
        return investors;
    }

    public void setInvestors(List<Investor> investors) {
        this.investors = investors;
    }

    public void setInvestor(Investor investor) {
        investors.add(investor);
    }

    public Startup() {
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInvestment() {
        return investment;
    }

    public void setInvestment(Integer investment) {
        this.investment = investment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Startup startup = (Startup) o;
        return Objects.equals(id, startup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Startup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", investment=" + investment +
                ", owner=" + owner +
                ", investors=" + investors +
                '}';
    }
}
