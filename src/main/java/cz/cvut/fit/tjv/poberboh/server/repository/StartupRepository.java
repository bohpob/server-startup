package cz.cvut.fit.tjv.poberboh.server.repository;

import cz.cvut.fit.tjv.poberboh.server.entity.Investor;
import cz.cvut.fit.tjv.poberboh.server.entity.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StartupRepository extends JpaRepository<Startup, Integer> {
    Optional<Startup> findByName(String name);

    List<Startup> findAll();
}
