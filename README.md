# db-jpa
Boilerplate code "Spring JPA Repository - Like". 

Code base on Hibernate with purpose use it for project that not using Spring Boot.
Example:

Model Object: 
```java
@Entity
Class User {

  @Id
  Integer id;
  
  String name;
}
```

Then, we create a repository for above Model
```java
Class UserRepository extends CrudRepositoryImpl<User, Integer> {
}
```

And, we have some basic operator with database:
```java
//init repository
UserRepository userRepository = new UserRepository();

//persist entity
userRepository.save(new User("ndhcoder"));

//find an entity
User user = userRepository.findById(1);

//find all entities
List<User> = userRepository.findAll();

//pagination
Page<User> pages = userRepository.findAll(PageRequest.of(1, 10));
log.info("page results: {}", pages.getContent());
log.info("total element: {}", pages.getTotalElements());

... so on
```
