package log.tenant.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import log.tenant.service.entity.Device;
import log.tenant.service.entity.Tenant;
import log.tenant.service.repository.DeviceRepository;
import log.tenant.service.repository.TenantRepository;
import log.tenant.service.services.TenantService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class TenantServiceImpl implements TenantService {
    @Autowired
    private TenantRepository tenantRepository;
    
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    @Override
    public Tenant getTenantById(Long id) {
        return tenantRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Tenant createTenant(Tenant tenant) {
        List<Device> managedDevices = new ArrayList<>();
        
        for (Device device : tenant.getDevices()) {
            Optional<Device> existingDevice = deviceRepository.findByDeviceId(device.getDeviceId());
            if (existingDevice != null) {
                managedDevices.add(existingDevice.get()); // Reuse existing device
            } else {
                managedDevices.add(device); // New device, should be managed
            }
        }
        
        tenant.setDevices(managedDevices); // Set managed devices
        return tenantRepository.save(tenant);
    }

    @Override
    @Transactional
    public Tenant updateTenant(Long id, Tenant tenantDetails) {
        try {
            Tenant tenant = tenantRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tenant not found with id " + id));

            // Update tenant details
            tenant.setTenantId(tenantDetails.getTenantId());
            tenant.setName(tenantDetails.getName());
            tenant.setEmail(tenantDetails.getEmail());

            // Update devices with reused IDs
            for (Device device : tenantDetails.getDevices()) {
                Optional<Device> existingDevice = deviceRepository.findByDeviceId(device.getDeviceId());
                existingDevice.ifPresent(d -> device.setId(d.getId())); // Reuse existing device ID if found
            }

            tenant.setDevices(tenantDetails.getDevices()); // Set devices with reused IDs

            return tenantRepository.save(tenant); // Save updated tenant
        } catch (Exception e) {
            // Log the exception or handle it as per your application's error handling strategy
            throw new RuntimeException("Failed to update tenant: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteTenant(Long id) {
        tenantRepository.deleteById(id);
    }
}

