package com.embarkx.jobms.job.clients;

import com.embarkx.jobms.job.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//- L'annotazione @FeignClient viene utilizzata per dichiarare un client REST che comunicherà
//  con un altro microservizio (in questo caso "COMPANY-SERVICE" - se guardiamo su Eureka vediamo che si chiama proprio COMPANY-SERVICE, il nome dell'app company).
//  Permette di semplificare le chiamate HTTP tra microservizi usando un'interfaccia Java.
//
//- In questo caso, il JobService utilizza questo client per ottenere i dati di una Company
//  tramite il suo ID. Quando viene chiamato il metodo getCompany(),
//  Feign invia automaticamente una richiesta GET all'endpoint /companies/{id} del microservizio "COMPANY-SERVICE"
//  e converte la risposta in un oggetto Company.
@FeignClient(name = "COMPANY-SERVICE")
public interface CompanyClient {

    //dobbiamo creare anche il mapping e prenderemo l'id passato dal metodo
    @GetMapping("/companies/{id}")
    Company getCompany(@PathVariable Long id);
}
