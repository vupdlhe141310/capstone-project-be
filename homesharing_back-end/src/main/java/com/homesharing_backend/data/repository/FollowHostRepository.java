package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.FollowHost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FollowHostRepository extends JpaRepository<FollowHost, Long> {

    @Query("SELECT count(f.host.id) FROM FollowHost f WHERE f.host.id= :hostID")
    Long totalFollowHostByCustomer(Long hostID);

    FollowHost getFollowHostById(Long id);

    int countFollowHostByHost_Id(Long hostID);

    int countFollowHostByHost_IdAndStatus(Long hostID, int status);
}
