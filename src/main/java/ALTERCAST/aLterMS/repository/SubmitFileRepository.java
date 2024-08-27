package ALTERCAST.aLterMS.repository;

import ALTERCAST.aLterMS.domain.SubmitFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmitFileRepository extends JpaRepository<SubmitFile, Long> {
    List<SubmitFile> findBySubmitId(Long submitId);

    void deleteBySubmitId(Long submitId);
}
