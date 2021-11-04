package cz.cvut.fit.tjv.poberboh.server.converter;

import cz.cvut.fit.tjv.poberboh.server.dto.OwnerDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Owner;

public class OwnerConverter {

    public static Owner toModel(OwnerDTO ownerDTO) {
        return new Owner(1000, ownerDTO.getFirstname(), ownerDTO.getLastname());
    }

    public static OwnerDTO fromModel(Owner owner) {
        return new OwnerDTO(owner.getFirstname(), owner.getLastname());
    }

}
