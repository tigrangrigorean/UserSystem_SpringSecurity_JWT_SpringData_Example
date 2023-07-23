package com.usersystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usersystem.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	User findUserById(long id);
	User findUserByName(String name);
	
}
