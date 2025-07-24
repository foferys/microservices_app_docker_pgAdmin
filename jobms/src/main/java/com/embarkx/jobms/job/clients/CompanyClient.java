package com.embarkx.jobms.job.clients;

import com.embarkx.jobms.job.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// L'annotazione @FeignClient viene utilizzata per dichiarare un client REST che comunicherà
// con un altro microservizio (in questo caso "COMPANY-SERVICE").
// Permette di semplificare le chiamate HTTP tra microservizi usando un'interfaccia Java.
// In questo esempio, viene utilizzata per recuperare i dati dell'azienda (Company)
// a cui è assegnato un job nel JobService.
@FeignClient(name = "COMPANY-SERVICE")
public interface CompanyClient {

    //dobbiamo creare anche il mapping e prenderemo l'id passato dal metodo
    @GetMapping("/companies/{id}")
    Company getCompany(@PathVariable Long id);
}
