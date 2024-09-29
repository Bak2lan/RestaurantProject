package java14.repository;

import java14.dto.userDTO.UserResponse;
import java14.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select new java14.dto.userDTO.UserResponse(u.firstName,u.lastName,u.dateOfBirth,u.email,u.phoneNumber,u.role,u.experience) from User u where u.id=:id")
    UserResponse getByIdUser(Long id);
    @Query("select new java14.dto.userDTO.UserResponse(u.firstName,u.lastName,u.dateOfBirth,u.email,u.phoneNumber,u.role,u.experience) from User u")
    List<UserResponse>getAllUsers();

    boolean existsUserByFirstName(String firstName);
    boolean existsByEmail(String email);
    @Query("select u from User u join u.cheques c where c.id=:chequeId")
    Optional<User>getUserByChequeId(Long chequeId);

    Optional<User>getUserByEmail(String email);


}
