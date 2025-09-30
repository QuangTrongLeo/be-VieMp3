package be_viemp3.viemp3.config;

import be_viemp3.viemp3.entity.*;
import be_viemp3.viemp3.enums.GenreEnum;
import be_viemp3.viemp3.enums.ReportEnum;
import be_viemp3.viemp3.enums.RoleEnum;
import be_viemp3.viemp3.enums.SubscriptionEnum;
import be_viemp3.viemp3.repository.analytics.ReportStatusRepository;
import be_viemp3.viemp3.repository.subscription.SubscriptionRepository;
import be_viemp3.viemp3.repository.user.RoleRepository;
import be_viemp3.viemp3.repository.music.GenreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final GenreRepository genreRepository;
    private final RoleRepository roleRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ReportStatusRepository reportStatusRepository;

    public DataInitializer(GenreRepository genreRepository,
                           RoleRepository roleRepository,
                           SubscriptionRepository subscriptionRepository,
                           ReportStatusRepository reportStatusRepository) {
        this.genreRepository = genreRepository;
        this.roleRepository = roleRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.reportStatusRepository = reportStatusRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // --- Khởi tạo genres ---
        for (GenreEnum genreEnum : GenreEnum.values()) {
            if (!genreRepository.existsByName(genreEnum)) {
                Genre genre = new Genre();
                genre.setName(genreEnum);
                genreRepository.save(genre);
            }
        }

        // --- Khởi tạo roles ---
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (!roleRepository.existsByName(roleEnum)) {
                Role role = new Role();
                role.setName(roleEnum);
                roleRepository.save(role);
            }
        }

        // --- Khởi tạo subscriptions (chỉ tạo ví dụ demo) ---
        for (SubscriptionEnum subEnum : SubscriptionEnum.values()) {
            if (!subscriptionRepository.existsByName(subEnum)) { // kiểm tra từng loại
                Subscription sub = new Subscription();
                sub.setName(subEnum);
                subscriptionRepository.save(sub);
            }
        }

        // --- Khởi tạo report ---
        for (ReportEnum reportEnum : ReportEnum.values()) {
            if (!reportStatusRepository.existsByName(reportEnum)) {
                ReportStatus status = new ReportStatus();
                status.setName(reportEnum);
                reportStatusRepository.save(status);
            }
        }
    }
}
