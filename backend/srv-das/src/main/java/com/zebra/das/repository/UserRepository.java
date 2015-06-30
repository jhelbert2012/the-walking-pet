package com.zebra.das.repository;

import com.zebra.das.model.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
