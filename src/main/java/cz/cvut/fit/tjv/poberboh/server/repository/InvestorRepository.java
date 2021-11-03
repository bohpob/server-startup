package cz.cvut.fit.tjv.poberboh.server.repository;

import cz.cvut.fit.tjv.poberboh.server.entity.Investor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepository extends CrudRepository<Investor, Integer> {

}