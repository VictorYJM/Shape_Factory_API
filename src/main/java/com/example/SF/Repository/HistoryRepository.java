package com.example.SF.Repository;

import com.example.SF.Model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface HistoryRepository extends JpaRepository<History, UUID> {
    @Query(value = "SELECT * FROM SF.GET_HistoryByClient(:client)", nativeQuery = true)
    List<History> getByClientNoDate(@Param("client") UUID client);

    @Query(value = "SELECT * FROM SF.GET_History(:client, :date)", nativeQuery = true)
    List<History> getByClient(@Param("client") UUID client, @Param("date") LocalDate date);
}
