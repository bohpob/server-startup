package cz.cvut.fit.tjv.poberboh.server.dto;

import cz.cvut.fit.tjv.poberboh.server.entity.Owner;

public class StartupDTO {

    private String name;
    private Integer investment;

    public StartupDTO(String name, Integer investment) {
        this.name = name;
        this.investment = investment;
    }

    public StartupDTO(String name, Integer investment, Owner owner) {
        this.name = name;
        this.investment = investment;
    }

    public StartupDTO() {
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

}
