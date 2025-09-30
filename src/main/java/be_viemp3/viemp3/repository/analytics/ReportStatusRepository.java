package be_viemp3.viemp3.repository.analytics;

import be_viemp3.viemp3.entity.ReportStatus;
import be_viemp3.viemp3.enums.ReportEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportStatusRepository extends JpaRepository<ReportStatus, Long> {
    boolean existsByName(ReportEnum report);
}
