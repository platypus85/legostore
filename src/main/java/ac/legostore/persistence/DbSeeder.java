package ac.legostore.persistence;

import ac.legostore.model.DeliveryInfo;
import ac.legostore.model.LegoSet;
import ac.legostore.model.LegoSetDifficulty;
import ac.legostore.model.ProductReview;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

@Service
public class DbSeeder implements CommandLineRunner {

    private MongoTemplate mongoTemplate;

    public DbSeeder(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) {

        this.mongoTemplate.dropCollection(LegoSet.class);

        /*
         * Lego sets
         */

        LegoSet milleniumFalcon = new LegoSet(
                "Millenium Falcon",
                "Star Wars",
                LegoSetDifficulty.HARD,
                new DeliveryInfo(LocalDate.now().plusDays(1), 30, true),
                Arrays.asList(new ProductReview("Dan", 1), new ProductReview("John", 4))

        );

        LegoSet skyPolice = new LegoSet(
                "Sky Police Air Base",
                "City",
                LegoSetDifficulty.MEDIUM,
                new DeliveryInfo(LocalDate.now().plusDays(3), 50, true),
                Arrays.asList(new ProductReview("Dan", 5), new ProductReview("Jane", 4), new ProductReview("Mark", 1))

        );

        Collection<LegoSet> initialProducts = Arrays.asList(milleniumFalcon,skyPolice);

        this.mongoTemplate.insertAll(initialProducts);

    }
}
