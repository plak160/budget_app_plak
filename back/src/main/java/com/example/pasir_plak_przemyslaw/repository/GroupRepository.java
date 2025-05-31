package com.example.pasir_plak_przemyslaw.repository;

import com.example.pasir_plak_przemyslaw.model.Group;
import com.example.pasir_plak_przemyslaw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findByMemberships_User(User user);
}
