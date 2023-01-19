package com.nemirovsky.tokenauthpaymentapiapp.repository;

import com.nemirovsky.tokenauthpaymentapiapp.model.Role;
import com.nemirovsky.tokenauthpaymentapiapp.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Roles name);
}
