package cz.cvut.fit.tjv.poberboh.server.dto;

public class OwnerDTO {

    public String firstname;

    public String lastname;

    public OwnerDTO(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public OwnerDTO() {
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
