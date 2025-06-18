package com.embarkx.companyms.company.impl;

import java.util.List;
import java.util.Optional;

import com.embarkx.companyms.company.Company;
import com.embarkx.companyms.company.CompanyRepository;
import com.embarkx.companyms.company.CompanyService;
import org.springframework.stereotype.Service;


@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompany() {

        List<Company> companies = companyRepository.findAll();

        return companies;

    }

    @Override
    public boolean updateCompany(Company company, Long id) {
      

        //usiamo optional -> contiene anche il metodo isPresent() che verifica se un oggetto è presente
        Optional<Company> companyOptional = companyRepository.findById(id);

        if (companyOptional.isPresent()) {
            //se è presente lo prendiamo e poi lo modifichiamo
            Company comp = companyOptional.get();
            comp.setName(company.getName());
            comp.setDescription(company.getDescription());

            companyRepository.save(comp);
            return true;
        }
     
        return false;
    }

    @Override
    public void createCompany(Company company) {
        
        companyRepository.save(company);

    }

    @Override
    public boolean deleteCompanyById(Long id) {
        
        boolean ris = false;

        if(companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }

        return ris;
        

    }


    @Override
    public Company getCompanyById(Long id) {

        //ritorna la company se c'è con quell'id altrimenti ritorna null
        return companyRepository.findById(id).orElse(null);
       
    }


}
