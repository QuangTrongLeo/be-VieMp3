package be_viemp3.viemp3.repository.finance;

import be_viemp3.viemp3.entity.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Packages, String> {
}
