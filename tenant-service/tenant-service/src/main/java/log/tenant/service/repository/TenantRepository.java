package log.tenant.service.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import log.tenant.service.entity.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Tenant t WHERE t.id = :tenantId")
    void deleteById(Long tenantId);
    
    Optional<Tenant> findByTenantId(String tenantId);
}

