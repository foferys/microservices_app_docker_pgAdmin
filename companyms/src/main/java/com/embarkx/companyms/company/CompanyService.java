package com.embarkx.companyms.company;

import com.embarkx.companyms.dto.ReviewMessage;

import java.util.List;



public interface CompanyService {

    List<Company> getAllCompany();

    void createCompany(Company company);

    boolean updateCompany(Company company, Long id);

    boolean deleteCompanyById(Long id);

    Company getCompanyById(Long id);

    //questo serve nella classe reviewMessageConsumer p
    public void updateCompanyRating(ReviewMessage reviewMessage);
   

}   
