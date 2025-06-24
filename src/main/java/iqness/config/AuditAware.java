package iqness.config;


import java.util.Optional;


import org.springframework.data.domain.AuditorAware;

import static iqness.utils.Utility.getUserName;


public class AuditAware implements AuditorAware<String> {

    public Optional<String> getCurrentAuditor() {
        return Optional.of(getUserName());
    }


}
