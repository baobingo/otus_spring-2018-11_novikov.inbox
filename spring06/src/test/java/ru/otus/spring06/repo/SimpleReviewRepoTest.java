package ru.otus.spring06.repo;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring06.domain.Book;
import ru.otus.spring06.domain.Review;

import java.util.List;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import({SimpleReviewRepo.class, SimpleBookRepo.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SimpleReviewRepoTest {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private BookRepo bookRepo;

    @Test
    void count() {
        Assert.assertEquals(2, reviewRepo.count());
    }

    @Test
    void insert() {
        Book book  = bookRepo.getByID(1);
        reviewRepo.insert(new Review("Troll #3", "Masterpeace", book));
        Assert.assertEquals(3, reviewRepo.count());
    }


    @Test
    void deleteById() {
        Book book  = bookRepo.getByID(1);
        Review review = new Review("Troll #3", "Masterpeace", book);
        reviewRepo.deleteById(review.getId());
        Assert.assertEquals(2, reviewRepo.count());
    }

    @Test
    void getByBook() {
        List<Review> list = reviewRepo.getByBook(bookRepo.getByID(1));
        Assert.assertEquals(1, list.size());
    }
}