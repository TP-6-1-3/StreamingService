package ru.vsu.csf.asashina.musicmanBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Verification;

import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, String> {

    Optional<Verification> findByCode(String code);
}
