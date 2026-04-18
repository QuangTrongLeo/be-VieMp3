package be_viemp3.viemp3.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class VnPayConfig {

    @Value("${vnp.url}")
    private String vnpPayUrl;

    @Value("${vnp.return.url}")
    private String vnpReturnUrl;

    @Value("${vnp.tmn.code}")
    private String vnpTmnCode;

    @Value("${vnp.hash.secret}")
    private String vnpHashSecret;

    @Value("${vnp.api.url}")
    private String vnpApiUrl;

}