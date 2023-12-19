package com.user888mk.pokemonreview.api.repository;

import com.user888mk.pokemonreview.api.models.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void reviewRepository_saveAll_ReturnSavedReview() {

        Review review = new Review().builder()
                .title("review_test")
                .content("test content")
                .stars(5)
                .build();

        Review save = reviewRepository.save(review);

        Assertions.assertThat(save).isNotNull();
        Assertions.assertThat(save.getId()).isGreaterThan(0);
    }

    @Test
    public void setReviewRepository_getAll_returnsMoreThanOneReview() {
        Review review1 = new Review().builder()
                .title("review_test")
                .content("test content")
                .stars(5)
                .build();

        Review review2 = new Review().builder()
                .title("review_test2")
                .content("test content2")
                .stars(5)
                .build();

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        List<Review> all = reviewRepository.findAll();

        Assertions.assertThat(all).isNotNull();
        Assertions.assertThat(all).hasSizeGreaterThan(1);

    }

    @Test
    public void reviewRepository_FindById_ReturnSavedReview() {

        Review review = new Review().builder()
                .title("review_test")
                .content("test content")
                .stars(5)
                .build();

        Review save = reviewRepository.save(review);
        Review review1 = reviewRepository.findById(review.getId()).get();

        Assertions.assertThat(review1).isNotNull();
    }

    @Test
    public void reviewRepository_update_ReturnReview() {

        Review review = new Review().builder()
                .title("review_test")
                .content("test content")
                .stars(5)
                .build();

        reviewRepository.save(review);

        Review review1 = reviewRepository.findById(review.getId()).get();
        review1.setContent("updated content");
        review1.setTitle("updated title");
        review1.setStars(3);
        Review updatedReview = reviewRepository.save(review1);


        Assertions.assertThat(updatedReview).isNotNull();
        Assertions.assertThat(updatedReview.getId()).isGreaterThan(0);
        assertEquals(updatedReview.getContent(), "updated content");
        assertEquals(updatedReview.getTitle(), "updated title");
        assertEquals(updatedReview.getStars(), 3);

    }

    @Test
    public void reviewRepository_deleteById_returnReviewIsEmpty() {
        Review review = new Review().builder()
                .title("review_test")
                .content("test content")
                .stars(5)
                .build();

        reviewRepository.save(review);
        reviewRepository.deleteById(review.getId());
        Optional<Review> byId = reviewRepository.findById(review.getId());

        Assertions.assertThat(byId).isEmpty();

    }
}