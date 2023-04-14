package ma.ensa.mvc_mongodb_demo_one.Dao;

import ma.ensa.mvc_mongodb_demo_one.Beans.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IUserMongoDBRepo extends MongoRepository<User, Long> {
    public Page<User> findByFullnameContaining(String hint, PageRequest page);
}
