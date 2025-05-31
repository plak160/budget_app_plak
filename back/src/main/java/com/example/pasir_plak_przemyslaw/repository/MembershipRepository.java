package com.example.pasir_plak_przemyslaw.repository;

import com.example.pasir_plak_przemyslaw.model.Membership;
import com.example.pasir_plak_przemyslaw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    List<Membership> findByGroupId(Long groupId);
}
