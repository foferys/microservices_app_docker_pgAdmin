package com.embarkx.jobms.job;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/jobs")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> findAll() {
        /**
        * Utilizziamo ResponseEntity per avere un controllo più preciso sulla risposta HTTP
        * (ad esempio sullo status code, intestazioni, ecc.).
        * 
        * In questo caso, usiamo il metodo statico 'ok()' che crea una risposta con:
        * - HTTP status 200 OK
        * - corpo della risposta contenente la lista dei Job
        */

        // return new ResponseEntity<>(jobService.findAll(), HttpStatus.OK);  //stesso modo di fare come sotto
        return ResponseEntity.ok(jobService.findAll());

    }


    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job) {

        jobService.createJob(job);
        return new ResponseEntity<>("job added successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {    //@PathVariable assicura che la variabile sia assegnata al long id

        Job job = jobService.getJobById(id);

        if (job != null) {
            return new ResponseEntity<>(job, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id) {

        boolean deleted = jobService.deleteJobById(id);

        if (deleted != false) {
            return new ResponseEntity<>("Job deleted.", HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    // @RequestMapping(value = "/jobs/{id}", method=RequestMethod.PUT)
    @PutMapping("/{id}") //usiamo questi mapping perché sono piu specifici e riducono anche il codice
    public ResponseEntity<String> updateJobById(@PathVariable Long id, @RequestBody Job updatedJob) {

  

        if (jobService.updateJob(id, updatedJob)) {
            
            return new ResponseEntity<>("Job updated.", HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}
