package com.embarkx.jobms.job.dto;

import com.embarkx.jobms.job.Job;
import com.embarkx.jobms.job.external.Company;

public class JobWithCompanyDTO {
    //vogliamo una risposta con job e company come json quindi abbiamo bisogno di questi
    //questi oggetti verranno mostrati agli user
    private Job job;
    private Company company;


    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
