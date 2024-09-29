package java14.repository;

import java14.entity.StopList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopListRepository extends JpaRepository<StopList,Long> {

}
