package log.tenant.service.services;

import java.util.List;

import log.tenant.service.entity.Tenant;

public interface TenantService {

	List<Tenant> getAllTenants();

	Tenant getTenantById(Long id);

	Tenant createTenant(Tenant tenant);

	Tenant updateTenant(Long id, Tenant tenantDetails);

	void deleteTenant(Long id);

}
