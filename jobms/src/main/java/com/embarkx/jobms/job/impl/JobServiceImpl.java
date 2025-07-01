package com.embarkx.jobms.job.impl;


import com.embarkx.jobms.job.AppConfig;
import com.embarkx.jobms.job.Job;
import com.embarkx.jobms.job.JobRepository;
import com.embarkx.jobms.job.JobService;
import com.embarkx.jobms.job.dto.JobWithCompanyDTO;
import com.embarkx.jobms.job.external.Company;
import com.embarkx.jobms.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class JobServiceImpl implements JobService {

    
    // private Long nextId = 1L; //tracciamento id, se è null mette 1 e cosi via
    
    //ignezione della repository con costruttore
    JobRepository jobRepository;

    //ignettiamo direttamente il restTemplate di cui abbiamo il bean in a AppConfig
    @Autowired
    RestTemplate restTemplate;


    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }




    @Override
    public List<JobWithCompanyDTO> findAll() {

        // Recupera tutti i lavori dal repository
        List<Job> jobs = jobRepository.findAll();

        // Converte ogni Job in JobWithCompanyDTO e raccoglie i risultati in una lista
        return jobs.stream()
                // Trasforma ogni oggetto Job in un JobWithCompanyDTO usando il metodo convertToDto
                .map(this::convertToDto)
                // Raccoglie gli elementi trasformati in una nuova lista
                .collect(Collectors.toList());


    }

    private JobWithCompanyDTO convertToDto(Job job) {

        //JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        //jobWithCompanyDTO.setJob(job);

        // consente di comunicare con il servizio Company tramite RestTemplate infatti mostra in console il dato richiesto
        //RestTemplate è una classe fornita da Spring che ti permette di fare richieste HTTP (GET, POST, PUT, DELETE, ecc.)
        // da un'app Java verso un altro servizio web REST.
        //RestTemplate restTemplate = new RestTemplate(); --> non usiamo piu cosi ma con il bean per avere load-balancing

        // restTemplate e quello ignettato dal bean che abbiamo in appConfig con il load-balancing che consente di usare
        //il nome del servizio che abbiamo nel discovery anziche mettere tutto l'indirizzo con localhost
        Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(), Company.class);

        JobWithCompanyDTO jobWithCompanyDTO = JobMapper.mapToJobWithCompanyDto(job, company);

        return jobWithCompanyDTO;

    }

    @Override
    public void createJob(Job job) {


        jobRepository.save(job);
    }

    @Override
    public JobWithCompanyDTO getJobById(Long id) {

        Job job = jobRepository.findById(id).orElse(null);

        //orElse serve per dire se non trovi quello che cerchi ritorna null
        return convertToDto(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        
        //usiamo il trycatch per gestire la non riuscita di cancellazione

        try {
            jobRepository.deleteById(id);
            return true;
            
        } catch (Exception e) {
            System.out.println("errore cancellazione: "+ e.getMessage() + "\n" + e.getCause());
            return false;
        }


    }

    @Override
    public boolean updateJob(Long idJob, Job updatedJob) {

        //usiamo optional -> contiene anche il metodo isPresent() che verifica se un oggetto è presente
        Optional<Job> jobOptional = jobRepository.findById(idJob);

        if (jobOptional.isPresent()) {
            //se è presente lo prendiamo e poi lo modifichiamo
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setLocation(updatedJob.getLocation());

            jobRepository.save(job);
            return true;
        }
     
        return false;

    }




}
