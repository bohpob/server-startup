package cz.cvut.fit.tjv.poberboh.server.dto;

import java.util.List;

public class InvestorDTO {

    private String firstname;
    private String lastname;
    private List<StartupDTO> startupList;

    public InvestorDTO(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public InvestorDTO(String firstname, String lastname, List<StartupDTO> startupList) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.startupList = startupList;
    }

    public List<StartupDTO> getStartupList() {
        return startupList;
    }

    public void setStartupList(List<StartupDTO> startupList) {
        this.startupList = startupList;
    }

    public InvestorDTO() {
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

}
