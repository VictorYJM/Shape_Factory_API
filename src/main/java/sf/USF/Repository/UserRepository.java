package sf.USF.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sf.USF.Entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(value = "SELECT * FROM V_User", nativeQuery = true)
    List<User> findAllOrder();

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User SET weight = :weight WHERE id = :id")
    void updateData(
            @Param("id") UUID id,
            @Param("weight") Double weight
    );

    @Modifying
    @Query("UPDATE User SET password = :password WHERE id = :id")
    void updatePassword(
            @Param("id") UUID id,
            @Param("password") String password
    );

    @Modifying
    @Query("UPDATE User SET active = FALSE WHERE id = :id")
    void exclude(@Param("id") UUID id);
}
