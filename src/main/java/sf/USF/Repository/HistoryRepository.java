package sf.USF.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sf.USF.Entity.History;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface HistoryRepository extends JpaRepository<History, UUID> {
    @Query(value = "SELECT * FROM SF.GET_HistoryByClient(:users)", nativeQuery = true)
    List<History> findByUserNoDate(@Param("users") UUID user);

    @Query(value = "SELECT * FROM SF.GET_History(:users, :date)", nativeQuery = true)
    List<History> findByUser(@Param("users") UUID user, @Param("date") LocalDate date);
}
