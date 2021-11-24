package cz.cvut.fit.tjv.poberboh.server.converter;

import cz.cvut.fit.tjv.poberboh.server.dto.StartupDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Startup;

public class StartupConverter {

    public static Startup toModel(StartupDTO startupDTO) {
        return new Startup(1000, startupDTO.getName(), startupDTO.getInvestment());
    }

    public static StartupDTO fromModel(Startup startup) {
        return new StartupDTO(startup.getName(), startup.getInvestment(), startup.getOwner());
    }

}
