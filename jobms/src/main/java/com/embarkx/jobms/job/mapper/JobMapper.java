package com.embarkx.jobms.job.mapper;

import com.embarkx.jobms.job.Job;
import com.embarkx.jobms.job.dto.JobDTO;
import com.embarkx.jobms.job.external.Company;
import com.embarkx.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
    //questa classe contiene un metodo per fare il mapper, impostare in un singolo oggetto job anche l'0oggetto company

    public static JobDTO mapToJobWithCompanyDto (Job job, Company company, List<Review> reviews) {

        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);

        return jobDTO;

    }
}
