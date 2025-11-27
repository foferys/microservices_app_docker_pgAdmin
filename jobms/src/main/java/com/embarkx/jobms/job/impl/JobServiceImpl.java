package com.embarkx.jobms.job.impl;

import com.embarkx.jobms.job.Job;
import com.embarkx.jobms.job.JobRepository;
import com.embarkx.jobms.job.JobService;
import com.embarkx.jobms.job.clients.CompanyClient;
import com.embarkx.jobms.job.clients.ReviewClient;
import com.embarkx.jobms.job.dto.JobDTO;
import com.embarkx.jobms.job.external.Company;
import com.embarkx.jobms.job.external.Review;
import com.embarkx.jobms.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class JobServiceImpl implements JobService {

    
    // private Long nextId = 1L; //tracciamento id, se è null mette 1 e cosi via
    
    //ignezione della repository con costruttore
    JobRepository jobRepository;

    //ignettiamo direttamente il restTemplate di cui abbiamo il bean in a AppConfig (questa serve se si usa
    // il "modo" con RestTemplate e non con openFeign)
    @Autowired
    RestTemplate restTemplate;

    private CompanyClient companyClient;
    private ReviewClient reviewClient;



    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {

        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<JobDTO> findAll() {

        // Recupera tutti i lavori dal repository
        List<Job> jobs = jobRepository.findAll();

        // Converte ogni Job in JobWithCompanyDTO e raccoglie i risultati in una lista
        return jobs.stream()
                // Trasforma ogni oggetto Job in un JobWithCompanyDTO usando il metodo convertToDto
                .map(this::convertToDto)
                // Raccoglie gli elementi trasformati in una nuova lista
                .collect(Collectors.toList());


    }

    private JobDTO convertToDto(Job job) {

        //JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        //jobWithCompanyDTO.setJob(job);

        //- Consente di comunicare con il servizio Company tramite RestTemplate infatti mostra in console il dato richiesto
        //- RestTemplate è una classe fornita da Spring che ti permette di fare richieste HTTP (GET, POST, PUT, DELETE, ecc.)
        //  da un'app Java verso un altro servizio web REST.
        //RestTemplate restTemplate = new RestTemplate(); --> non usiamo piu cosi ma con il bean per avere load-balancing

        //restTemplate è quello ignettato dal bean che abbiamo in appConfig con il load-balancing che consente di usare
        //il nome del servizio che abbiamo nel discovery anziche mettere tutto l'indirizzo con localhost
        //Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(), Company.class);


        // Recuperiamo dinamicamente tutte le recensioni (Review) associate a un'azienda specifica
        // tramite una chiamata HTTP al servizio "REVIEW-SERVICE" usando RestTemplate.

        /* Utilizziamo il metodo exchange(), più flessibile rispetto a getForObject():
           - getForObject è adatto per ricevere un singolo oggetto;
           - exchange è utile quando dobbiamo gestire collezioni generiche (es. List<Review>). */
        //ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
        //        "http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(), // URL del servizio review con companyId come parametro query
        //        HttpMethod.GET,        // Specifica che il metodo HTTP è GET
        //        null,                  // Non inviamo un corpo della richiesta (request body), quindi passiamo null
        //        new ParameterizedTypeReference<List<Review>>() {} // Indichiamo il tipo generico atteso nella risposta (List<Review>)
        //);

        // Estraiamo il corpo della risposta HTTP, che contiene la lista di oggetti Review
        //List<Review> reviews = reviewResponse.getBody();

        /*---- metodo con OPEN FEIGN - comuicazione con Server discovery EUREKA ----*/
        //---- stessa cosa dei due sopra ma con openFeign riducendo molto il codice
        //---- se non usiamo il metodo con RestTemplate possiamo eliminare il @Bean relativo in AppConfig.java
        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job, company, reviews);

        return jobDTO;

    }

    @Override
    public void createJob(Job job) {


        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {

        //orElse serve per dire se non trovi quello che cerchi ritorna null
        Job job = jobRepository.findById(id).orElse(null);

        /* convertToDto è il metodo che abbiamo sopra che abbiamo creato per comunicare con il servizio Company tramite RestTemplate
        * o con openFeign */
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
