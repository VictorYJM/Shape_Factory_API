package com.example.SF.Repository;

import com.example.SF.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IClient extends JpaRepository<Client, UUID> {
    @Query(value = "SELECT * FROM V_ClientOrder", nativeQuery = true)
    List<Client> getAll();

    @Query(value = "SELECT * FROM SF.GET_ClientByName(:name)", nativeQuery = true)
    Client getByName(@Param("name") String name);

    @Query(value = "SELECT * FROM SF.GET_ClientByEmail(:email)", nativeQuery = true)
    Client getByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE Client SET client_weight = :weight WHERE client_id = :id")
    void updateData(
            @Param("id") UUID id,
            @Param("weight") Double weight
    );

    @Modifying
    @Query("UPDATE Client SET client_password = :password WHERE client_id = :id")
    void updatePassword(
            @Param("id") UUID id,
            @Param("password") String password
    );

    @Modifying
    @Query("UPDATE Client SET client_active = FALSE WHERE client_id = :id")
    void exclude(@Param("id") UUID id);
}
