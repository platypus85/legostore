package ac.legostore.api;

import ac.legostore.model.LegoSet;
import ac.legostore.model.LegoSetDifficulty;
import ac.legostore.model.QLegoSet;
import ac.legostore.persistence.LegoSetRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("legostore/api")
public class LegoStoreController {

    private LegoSetRepository legoSetRepository;

    public LegoStoreController(LegoSetRepository legoSetRepository) {
        this.legoSetRepository = legoSetRepository;
    }

    @PostMapping
    public void insert(@RequestBody LegoSet legoSet) {
        this.legoSetRepository.insert(legoSet);
    }

    @GetMapping("/all")
    public Collection<LegoSet> getAll() {
        Sort sortByThemeAsc = Sort.by("theme").ascending();
        return this.legoSetRepository.findAll(sortByThemeAsc);
    }

    @PutMapping
    public void update(@RequestBody LegoSet legoSet) {
        this.legoSetRepository.save(legoSet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.legoSetRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public LegoSet byId(@PathVariable String id) {
        return this.legoSetRepository.findById(id).orElse(null);
    }

    @GetMapping("/byTheme/{theme}")
    public Collection<LegoSet> byTheme(@PathVariable String theme) {
        Sort sortbyThemeAsc = Sort.by("theme").ascending();
        return this.legoSetRepository.findAllByThemeContains(theme, sortbyThemeAsc);
    }

    @GetMapping("/hardThatStartsWithM")
    public Collection<LegoSet> hardThatStartsWithM() {
        return this.legoSetRepository.findAllByDifficultyAndNameContains(LegoSetDifficulty.HARD, "M");
    }

    @GetMapping("/byDeliveryFeeLessThan/{price}")
    public Collection<LegoSet> byDeliveryFeeLessThan(@PathVariable int price) {
        return this.legoSetRepository.findAllByDeliveryFeeLessThan(price);
    }

    @GetMapping("/byGreatReviews")
    public Collection<LegoSet> byGreatReviews() {
        return this.legoSetRepository.findAllByGreatReviews();
    }

    @GetMapping("/bestBuys")
    public Collection<LegoSet> bestBuys() {
        QLegoSet query = new QLegoSet("query");
        BooleanExpression inStockFilter = query.deliveryInfo.inStock.isTrue();
        Predicate smallDeliveryFeeFilter = query.deliveryInfo.deliveryFee.lt(50);
        Predicate hasGreatReviews = query.reviews.any().rating.eq(10);

        Predicate bestBuysFilter = inStockFilter.and(smallDeliveryFeeFilter).and(hasGreatReviews);

        return (Collection<LegoSet>) this.legoSetRepository.findAll(bestBuysFilter);
    }

}
