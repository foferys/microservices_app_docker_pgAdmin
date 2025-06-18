package com.embarkx.reviewms.review;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    


    @GetMapping
    public ResponseEntity<List<Review>> getReviewsPerCompany(@RequestParam Long companyId) {

        return (reviewService.getAllReviews(companyId) != null)?  new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);


    }


    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review) {


        if(reviewService.addReview(companyId, review)) {

            return new ResponseEntity<>("Review aggiunta correttamente!", HttpStatus.CREATED);
        }
        
        return new ResponseEntity<>("Errore Review non aggiunta", HttpStatus.NOT_FOUND);
        


    }
    
    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId) {

        return new ResponseEntity<>(reviewService.getReview(reviewId), HttpStatus.OK);
     
    }
    
    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Review review ) {



        if(reviewService.updateReview(reviewId, review)) {
            System.out.println("Review aggiornata correttamente");

            return new ResponseEntity<>("Review aggiornata", HttpStatus.OK);
        }else {
            System.out.println("Review non aggiornata");
            return new ResponseEntity<>("ERROR: Review non aggiornata", HttpStatus.NOT_FOUND);
        }
        
        


    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {


        if(reviewService.deleteReview(reviewId)) {

            return new ResponseEntity<>("Review cancellata", HttpStatus.OK);
        }
        
        return new ResponseEntity<>("ERROR: Review non cancellata", HttpStatus.NOT_FOUND);
        


    }

}
