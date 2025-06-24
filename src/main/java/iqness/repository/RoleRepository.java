package iqness.repository;

import iqness.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Query("select r from Role r where r.name=:role ")
    Role getRoleByName(@Param("role") String role);

}
