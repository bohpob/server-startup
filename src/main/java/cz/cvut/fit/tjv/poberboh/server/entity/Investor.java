package cz.cvut.fit.tjv.poberboh.server.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "investor")
public class Investor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String firstname;
    private String lastname;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "invested",
            joinColumns = @JoinColumn(name = "investor_id"),
            inverseJoinColumns = @JoinColumn(name = "startup_id"))
    private List<Startup> investments = new ArrayList<>();

    public Investor() {
    }

    public Investor(String username, String firstname, String lastname, List<Startup> investments) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.investments = investments;
    }

    public Investor(Integer id, String username, String firstname, String lastname) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Investor(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public List<Startup> getInvestments() {
        return investments;
    }

    public List<Integer> getStartupsId() {
        List<Integer> list = new ArrayList<>();
        for (Startup startup : investments) {
            list.add(startup.getId());
        }
        return list;
    }

    public void setInvestments(Startup startup) {
        this.investments.add(startup);
    }

    public Integer getId() {
        return id;
    }

    public void removeInvested(Startup startup) {
        investments.remove(startup);
    }

    public void removeAllInvested() {
        for (Startup startup : this.investments) {
            investments.remove(startup);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setInvestments(List<Startup> investments) {
        this.investments = investments;
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
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", investments=" + investments +
                '}';
    }
}
