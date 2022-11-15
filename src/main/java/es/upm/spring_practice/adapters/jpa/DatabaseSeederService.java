package es.upm.spring_practice.adapters.jpa;

import es.upm.spring_practice.adapters.jpa.shop.ShopSeederService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository //@Profile("dev")
public class DatabaseSeederService {

    private final ShopSeederService shopSeederService;

    @Autowired
    public DatabaseSeederService(ShopSeederService shopSeederService) {
        this.shopSeederService = shopSeederService;
        this.deleteAll();
        this.seedDatabase();
    }

    public void seedDatabase() {
        this.shopSeederService.seedDatabase();
    }

    public void deleteAll() {
        this.shopSeederService.deleteAll();
    }

    public void reSeedDatabase() {
        this.deleteAll();
        this.seedDatabase();
    }
}
