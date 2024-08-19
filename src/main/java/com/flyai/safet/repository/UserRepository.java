package com.flyai.safet.repository;

import com.flyai.safet.entity.Setting;
import com.flyai.safet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
