package cz.cvut.fit.tjv.poberboh.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Startup implements Serializable {

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
