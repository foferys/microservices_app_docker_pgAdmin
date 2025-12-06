package com.embarkx.jobms.job.external;

// questa classe company appartiene a job ed è creata per poter comunicare con il ms di Review, per ricereare la review
public class Review {

    private Long id;

    private String title;
    private String description;
    private double rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}
