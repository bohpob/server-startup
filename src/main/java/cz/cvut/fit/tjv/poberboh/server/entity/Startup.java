package cz.cvut.fit.tjv.poberboh.server.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Startup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer investment;

    public Startup(Integer id, String name, Integer investment) {
        this.id = id;
        this.name = name;
        this.investment = investment;
    }

    public Startup(String name, Integer investment) {
        this.name = name;
        this.investment = investment;
    }

    public Startup() {
    }

    @ManyToOne
    @JoinColumn(name = "owner_name")
    private Owner owner;

    @ManyToMany
    @JoinTable(name = "investments",
            joinColumns = @JoinColumn(name = "startup_id"),
            inverseJoinColumns = @JoinColumn(name = "investor_id")
    )
    private List<Investor> investorList;

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
                '}';
    }
}
