package com.refactoring.rekall.repository;

import com.refactoring.rekall.entity.UserDelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDelRepository extends JpaRepository<UserDelEntity, String> {
}
