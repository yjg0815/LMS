package ALTERCAST.aLterMS.repository;

import ALTERCAST.aLterMS.domain.UserSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserSectionRepository extends JpaRepository<UserSection, Long> {

    Optional<UserSection> findByIdAndSectionId(Long userId, Long secId);

    @Query("SELECT us FROM UserSection us JOIN FETCH us.section WHERE us.user.id = :id")
    List<UserSection> findAllByUserIdFetchJoinSection(@Param("id") Long userId);
}
