package ac.legostore.persistence;

import ac.legostore.model.AvgRatingModel;
import ac.legostore.model.LegoSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private MongoTemplate mongoTemplate;

    public ReportService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<AvgRatingModel> getAvgRatingReport(){
        ProjectionOperation projectToMatchModel = Aggregation.project()
                .andExpression("name").as("productName")
                .andExpression("{$avg : '$reviews.rating'}").as("avgRating");

        Aggregation avgRatingAggregation = Aggregation.newAggregation(LegoSet.class, projectToMatchModel);

        return this.mongoTemplate.aggregate(avgRatingAggregation, LegoSet.class, AvgRatingModel.class).getMappedResults();
    }
}
