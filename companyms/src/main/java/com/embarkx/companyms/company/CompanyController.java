package com.embarkx.companyms.company;

import java.util.List;

import com.embarkx.companyms.company.impl.CompanyServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

// 🔸@RestController Significa che tutti i metodi restituiscono direttamente il corpo della risposta HTTP (JSON, String, ecc.), senza passare da una view (tipo JSP o HTML).
// 🟨 @Controller È usato per applicazioni MVC tradizionali (tipo Spring MVC con pagine HTML, JSP, Thymeleaf...).
@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyServiceImpl companyServiceImpl;

    
    private CompanyService companyService;

    public CompanyController(CompanyService companyService, CompanyServiceImpl companyServiceImpl) {
        this.companyService = companyService;
        this.companyServiceImpl = companyServiceImpl;
    }
    
    @GetMapping
    public List<Company> getAllCompany() {
        return companyService.getAllCompany();
    }


    @PostMapping
    public ResponseEntity<String> addCompany(@RequestBody Company company) {
        try {
            
            companyService.createCompany(company);
            return new ResponseEntity<>("aggiunta company andata abuon fine" , HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("errore nell'aggiunta di una company" + e.getCause());
            return new ResponseEntity<>("aggiunta company andata male malissimo" , HttpStatus.NOT_FOUND);

        }
   
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody Company company) {

        
        return (companyService.updateCompany(company, id)) ? new ResponseEntity<>("aggiornamento effettuato", HttpStatus.OK): new ResponseEntity<>("errore nell'aggiornamento", HttpStatus.NOT_FOUND);


    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {


        return (companyServiceImpl.deleteCompanyById(id))? new ResponseEntity<>("company eliminata", HttpStatus.OK) : new ResponseEntity<>("company non eliminata", HttpStatus.NOT_FOUND)  ;

    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Long id) {

        return companyServiceImpl.getCompanyById(id) != null ? new ResponseEntity<>(companyServiceImpl.getCompanyById(id), HttpStatus.OK): new ResponseEntity<>(new Company(), HttpStatus.NOT_FOUND);  

    }


}
