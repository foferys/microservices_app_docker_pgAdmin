package com.embarkx.reviewms.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long>{

    // basta dichiararlo cosi e jpa si occuperà di fare tutto a runtime in automatico
    List<Review> findByCompanyId(Long companyId);

    


}
