package dev.FurkanUnal.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    public ReviewRepository reviewRepository;

    @Autowired
    public MongoTemplate mongoTemplate;

    public List<Review> allReviews(){
        return reviewRepository.findAll();
    }

    public Review createReview(String body, String imdbId){
        Review review = new Review(body);
        reviewRepository.insert(review);
        mongoTemplate.update(Movie.class).matching(Criteria.where("imdbId").is(imdbId)).apply(new Update().push("reviewIds").value(review)).first();
        return review;
    }
}
