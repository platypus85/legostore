package ac.legostore.persistence;

import ac.legostore.model.LegoSet;
import ac.legostore.model.LegoSetDifficulty;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LegoSetRepository extends MongoRepository<LegoSet,String>, QuerydslPredicateExecutor<LegoSet> {
    Collection<LegoSet> findAllByThemeContains(String theme, Sort sort);
    Collection<LegoSet> findAllByDifficultyAndNameContains(LegoSetDifficulty difficulty, String theme);
    @Query("{'delivery.deliveryFee':{$lt:?0}}")
    Collection<LegoSet> findAllByDeliveryFeeLessThan(int price);
    @Query("{'reviews.rating' : {$eq : 10}}")
    Collection<LegoSet> findAllByGreatReviews();
    Collection<LegoSet> findAllByThemeNotContains(String theme);
    @Query("{'delivery.inStock' : {$eq : true}}")
    Collection<LegoSet> findAllInStock();
}
