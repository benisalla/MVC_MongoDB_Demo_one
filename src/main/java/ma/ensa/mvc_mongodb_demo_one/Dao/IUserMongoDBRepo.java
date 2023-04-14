package ma.ensa.mvc_mongodb_demo_one.Dao;

import ma.ensa.mvc_mongodb_demo_one.Beans.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserMongoDBRepo extends MongoRepository<User, Long> {
}
