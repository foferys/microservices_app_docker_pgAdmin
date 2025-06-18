package com.embarkx.reviewms.review.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.embarkx.reviewms.review.Review;
import com.embarkx.reviewms.review.ReviewRepository;
import com.embarkx.reviewms.review.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService{


    private final ReviewRepository reviewRepository;


    
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;

    }



    @Override
    public List<Review> getAllReviews(Long companyId) {
        
        return reviewRepository.findByCompanyId(companyId);

    }
    
    @Override
    public boolean addReview(Long companyId, Review review) {



        if (companyId != null && review != null) {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        return false;


    }



    @Override
    public Review getReview(Long reviewId) {


        return reviewRepository.findById(reviewId).orElse(null);
        
        // Optional<Review> review = reviewRepository.findById(reviewId);
        // if (review.isPresent()) {
        //     return review.get();
        // }
        // return null;
        
        
    }
    
    
    
    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {

        Review review = reviewRepository.findById(reviewId).orElse(null);

        if (reviewId != null) {
            
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            review.setCompanyId(updatedReview.getCompanyId());
            reviewRepository.save(review);
            return true;

        }
        return false;

    }



    @Override
    public boolean deleteReview(Long reviewId) {

          // If no review found, return false
        // This means either the review does not exist or it does not belong to the specified company
        // If the review does not exist or does not belong to the specified company, return false
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            reviewRepository.delete(review);
            return true;
        }
        
        return false;


        // --- con monolitico ---
        // Find the review by companyId and reviewId
        // This approach assumes that the reviewRepository has a method to find reviews by companyId
        // List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        
        // Review r = reviews.stream()
        // .filter(review -> review.getId().equals(reviewId))
        // .findFirst()
        // .orElse(null);

        // if (r != null) {
        //     reviewRepository.delete(r);
        //     return true;
        // }
    }


}
