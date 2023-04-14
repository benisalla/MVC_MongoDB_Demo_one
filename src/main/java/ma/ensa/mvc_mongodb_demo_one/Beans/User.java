package ma.ensa.mvc_mongodb_demo_one.Beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User {
    @Id @MongoId(targetType = FieldType.OBJECT_ID)
    private Long id;
    private String fullname;
    private int age;
    private String email;
    private String password;
}