package cz.cvut.fit.tjv.poberboh.server.entity;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToMany(mappedBy = "investments")
    private List<Investor> investors = new ArrayList<>();

    public Startup(String name, Integer investment, Owner owner) {
        this.name = name;
        this.investment = investment;
        this.owner = owner;
    }

    public Startup(Integer id, String name, Integer investment) {
        this.id = id;
        this.name = name;
        this.investment = investment;
    }

    public Startup(String name, Integer investment) {
        this.name = name;
        this.investment = investment;
    }

    public List<Investor> getInvestors() {
        return investors;
    }

    public boolean investorExists(Investor investor) {
        for (Investor invest : this.investors) {
            if (investor.equals(invest)) {
                return true;
            }
        }
        return false;
    }

    public void setInvestors(List<Investor> investors) {
        this.investors = investors;
    }

    public void addInvestor(Investor investor) {
        investors.add(investor);
    }

    public void deleteInvestor(Investor investor) {
        investors.remove(investor);
    }

    public void deleteAllInvestors() {
        for (Investor investor : this.investors) {
            investors.remove(investor);
        }
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
        return Objects.equals(id, startup.id)
                && Objects.equals(name, startup.name)
                && Objects.equals(investment, startup.investment)
                && Objects.equals(owner, startup.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, investment, owner);
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
