package com.flyai.safet.repository;

import com.flyai.safet.entity.Setting;
import com.flyai.safet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {

    Optional<Setting> findByUser(User user);
}
