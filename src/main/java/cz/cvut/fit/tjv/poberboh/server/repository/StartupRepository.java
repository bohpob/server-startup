package cz.cvut.fit.tjv.poberboh.server.repository;

import cz.cvut.fit.tjv.poberboh.server.entity.Startup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StartupRepository extends CrudRepository<Startup, Integer> {
    Optional<Startup> findByName(String name);
}
