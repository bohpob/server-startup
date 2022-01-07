package cz.cvut.fit.tjv.poberboh.server.converter;

import cz.cvut.fit.tjv.poberboh.server.dto.StartupDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Startup;

import java.util.stream.Collectors;

public class StartupConverter {

    public static Startup toModel(StartupDTO startupDTO) {
        return new Startup(0, startupDTO.getName(), startupDTO.getInvestment());
    }

    public static StartupDTO fromModel(Startup startup) {
        return new StartupDTO(startup.getName(), startup.getInvestment(),
                startup.getInvestors().stream().map(InvestorConverter::fromModel).collect(Collectors.toList()));
    }
}
