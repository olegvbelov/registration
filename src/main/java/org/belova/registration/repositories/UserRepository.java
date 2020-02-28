package org.belova.registration.repositories;

import org.belova.registration.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Long>{
    List<User> findUserByEmail(String email);

}
