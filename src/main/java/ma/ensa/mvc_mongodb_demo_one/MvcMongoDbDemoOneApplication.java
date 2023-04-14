package ma.ensa.mvc_mongodb_demo_one;

import ma.ensa.mvc_mongodb_demo_one.Beans.User;
import ma.ensa.mvc_mongodb_demo_one.Dao.IUserMongoDBRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class MvcMongoDbDemoOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcMongoDbDemoOneApplication.class, args);
    }


    @Bean
    CommandLineRunner runner(IUserMongoDBRepo userRepo){
		return args -> {
//			User user = userRepo.findById(30L).orElse(null);
//			System.out.println(user);
			List<User> users = userRepo.findAll();
			if(users.size() == 0){
				for(int i=0; i<100; i++){
					Long id = (long) i;
					String fullname = "A"+i+" B"+i;
					String email = "A"+i+"B"+i+"@gmail.com";
					String password = (i+1)+""+(i+2)+""+(i+4)+""+(i+7);
					userRepo.insert(new User(id,fullname,i*10,email,password));
				}
			}
		};
	}
}
