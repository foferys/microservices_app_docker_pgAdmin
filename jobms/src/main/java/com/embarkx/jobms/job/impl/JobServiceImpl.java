package com.embarkx.jobms.job.impl;


import com.embarkx.jobms.job.Job;
import com.embarkx.jobms.job.JobRepository;
import com.embarkx.jobms.job.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class JobServiceImpl implements JobService {

    
    // private Long nextId = 1L; //tracciamento id, se è null mette 1 e cosi via
    
    //ignezione della repository con costruttore
    JobRepository jobRepository;


    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }




    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public void createJob(Job job) {

        jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {

        //orElse serve per dire se non trovi quello che cerchi ritorna null
        return jobRepository.findById(id).orElse(null);
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
