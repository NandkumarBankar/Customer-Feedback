package com.test.repository;

import com.test.domain.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserData,Long> {

    Optional<UserData> findByUserName(String userName);
    Optional<UserData> findByUserNameAndEmail(String userName,String email);

    UserData findByUserNameAndPassword(String userName, String password);




}
