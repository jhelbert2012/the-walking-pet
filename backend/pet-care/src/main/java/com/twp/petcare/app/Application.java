package com.twp.petcare.app;

import com.twp.petcare.bean.Owner;
import com.twp.petcare.bean.Pet;
import com.twp.petcare.repository.OwnerRepository;
import com.twp.petcare.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Calendar;

@Configuration
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = "com.twp.petcare.repository")
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private PetRepository petRepository;

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }

    public void run(String... strings) throws Exception {

        initializeOwners();
        initializePets();


    }

    private void initializeOwners() {
        ownerRepository.deleteAll();

        // save a couple of owners
        ownerRepository.save(new Owner(1, "79996406", "jhelbert", "password1", 'M', "Helbert", "Rico", "Cra 71b Bis 12 60", "3144460648", "jhelbert@gmail.com", Calendar.getInstance().getTime()));
        ownerRepository.save(new Owner(1, "55659669", "erika", "pass2", 'F', "Erika", "Giraldo", "Address2", "34344422233", "erikagiraldov@gmail.com", Calendar.getInstance().getTime()));

        // fetch all owners
        System.out.println("Owners found with findAll():");
        System.out.println("-------------------------------");
        for (Owner owner : ownerRepository.findAll()) {
            System.out.println(owner);
        }
        System.out.println();

        // fetch an individual owner
        System.out.println("Owners found with findByFirstName('jhelbert'):");
        System.out.println("--------------------------------");
        System.out.println(ownerRepository.findByUsername("jhelbert"));

        System.out.println("Customers found with findByDocumentTypeAndUsername(\"1\", \"jhelbert\", pageable):");
        System.out.println("--------------------------------");

        Pageable pageable = new PageRequest(0, 10);
        for (Owner owner : ownerRepository.findByDocumentTypeAndUsername("1", "jhelbert", pageable)) {
            System.out.println(owner);
        }

        for (Owner owner : ownerRepository.findByDocumentNumberAndUsernameAllIgnoreCase("79996406", "jhelbert", pageable)) {
            System.out.println(owner);
        }
    }

    private void initializePets() {
        petRepository.deleteAll();

        // save a couple of owners
        petRepository.save(new Pet("koke", 36, 'M', 43, 43, 40, 8, "black"));
        petRepository.save(new Pet("caicer", 54, 'M', 34, 56, 45, 20, "black"));

        // fetch all owners
        System.out.println("Owners found with findAll():");
        System.out.println("-------------------------------");
        for (Pet pet : petRepository.findAll()) {
            System.out.println(pet);
        }
        System.out.println();

        // fetch an individual owner
        System.out.println("Owners found with findByName('koke'):");
        System.out.println("--------------------------------");
        System.out.println(petRepository.findByName(("koke")));

        System.out.println("Customers found with findBySpecie(34):");
        System.out.println("--------------------------------");

        Pageable pageable = new PageRequest(0, 10);
        for (Pet pet : petRepository.findBySpecie(34)) {
            System.out.println(pet);
        }
        System.out.println("Customers found with findByColorIgnoreCase('black', pageable):");
        System.out.println("--------------------------------");
        for (Pet pet : petRepository.findByColorIgnoreCase("black", pageable)) {
            System.out.println(pet);
        }

        Pet pet = petRepository.getPetFor(new Owner("112232322"));

        System.out.println(pet);
    }
}
 