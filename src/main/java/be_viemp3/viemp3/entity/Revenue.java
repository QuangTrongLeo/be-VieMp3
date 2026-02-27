package be_viemp3.viemp3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "revenues")
public class Revenue {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private Double amount;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;
}

