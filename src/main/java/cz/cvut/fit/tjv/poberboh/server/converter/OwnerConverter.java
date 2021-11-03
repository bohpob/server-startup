package cz.cvut.fit.tjv.poberboh.server.converter;

import cz.cvut.fit.tjv.poberboh.server.dto.OwnerDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Owner;

import java.util.ArrayList;
import java.util.Collection;

public class OwnerConverter {

    public static Owner toModel(OwnerDTO ownerDTO) {
        return new Owner(ownerDTO.getFirstname(), ownerDTO.getLastname());
    }

    public static OwnerDTO fromModel(Owner owner) {
        return new OwnerDTO(owner.getFirstname(), owner.getLastname());
    }

    public static Collection<Owner> toModels(Collection<OwnerDTO> ownerDTOs) {
        Collection<Owner> owners = new ArrayList<>();
        ownerDTOs.forEach((ownerDTO) -> owners.add(toModel(ownerDTO)));
        return owners;
    }

    public static Collection<OwnerDTO> fromModels(Collection<Owner> owners) {
        return owners.stream().map(OwnerConverter::fromModel).toList();
    }

}
