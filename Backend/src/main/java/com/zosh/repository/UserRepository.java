package com.zosh.repository;

import com.zosh.model.Appuser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Appuser, Long> {

	public Appuser findByEmail(String email);

}
