package com.example.webproject.companymanagement.domain.app_user.data;

import com.example.webproject.companymanagement.domain.app_user.models.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification,Long> {

}
