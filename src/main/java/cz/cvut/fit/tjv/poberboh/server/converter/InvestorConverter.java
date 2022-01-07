package cz.cvut.fit.tjv.poberboh.server.converter;

import cz.cvut.fit.tjv.poberboh.server.dto.InvestorDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Investor;

public class InvestorConverter {

    public static Investor toModel(InvestorDTO investorDTO) {
        return new Investor(0, investorDTO.getUsername(), investorDTO.getFirstname(), investorDTO.getLastname());
    }

    public static InvestorDTO fromModel(Investor investor) {
        return new InvestorDTO(investor.getUsername(), investor.getFirstname(), investor.getLastname(), investor.getStartupsId());
    }
}
