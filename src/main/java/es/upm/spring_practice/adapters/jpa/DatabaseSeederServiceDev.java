package es.upm.spring_practice.adapters.jpa;

import es.upm.spring_practice.adapters.jpa.shop.ShopSeederServiceDev;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository //@Profile("dev")
public class DatabaseSeederServiceDev {

    private final UserSeederDev userSeederDev;
    private final ShopSeederServiceDev shopSeederServiceDev;

    @Autowired
    public DatabaseSeederServiceDev(UserSeederDev userSeederDev, ShopSeederServiceDev shopSeederServiceDev) {
        this.userSeederDev = userSeederDev;
        this.shopSeederServiceDev = shopSeederServiceDev;
        this.reSeedDatabase();
    }

    public void seedDatabase() {
        this.userSeederDev.seedDataBase();
        this.shopSeederServiceDev.seedDatabase();
    }

    public void deleteAll() {
        this.userSeederDev.deleteAllAndInitialize();
        this.shopSeederServiceDev.deleteAll();
    }

    public void reSeedDatabase() {
        this.deleteAll();
        this.seedDatabase();
    }
}
