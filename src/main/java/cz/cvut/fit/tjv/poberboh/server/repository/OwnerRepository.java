package cz.cvut.fit.tjv.poberboh.server.repository;

import cz.cvut.fit.tjv.poberboh.server.entity.Owner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Integer> {
    Optional<Owner> findByUsername(String username);
}
