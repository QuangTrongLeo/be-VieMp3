package be_viemp3.viemp3.entity;

import be_viemp3.viemp3.enums.ReportEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report_statuses")
public class ReportStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)  // lưu enum thành chuỗi (PENDING, RESOLVED)
    @Column(nullable = false, unique = true)
    private ReportEnum name;
}

