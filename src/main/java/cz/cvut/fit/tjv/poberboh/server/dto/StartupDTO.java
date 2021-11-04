package cz.cvut.fit.tjv.poberboh.server.dto;

public class StartupDTO {

    private String name;
    private Integer investment;

    public StartupDTO(String name, Integer investment) {
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
