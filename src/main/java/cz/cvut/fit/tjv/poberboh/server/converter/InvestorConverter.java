package cz.cvut.fit.tjv.poberboh.server.converter;

import cz.cvut.fit.tjv.poberboh.server.dto.InvestorDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Investor;

import java.util.stream.Collectors;

public class InvestorConverter {

    public static Investor toModel(InvestorDTO investorDTO) {
        return new Investor(1000, investorDTO.getFirstname(), investorDTO.getLastname());
    }

    public static InvestorDTO fromModel(Investor investor) {
        return new InvestorDTO(investor.getFirstname(), investor.getLastname(),
                investor.getInvested().stream().map(StartupConverter::fromModel).collect(Collectors.toList()));
    }
}
