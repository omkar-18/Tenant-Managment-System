package log.tenant.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import log.tenant.service.entity.Tenant;
import log.tenant.service.repository.TenantRepository;
import log.tenant.service.services.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tenants")
@Tag(name = "Tenant Management", description = "APIs for managing tenants")
public class TenantController {
    @Autowired
    private TenantService tenantService;
    
    @Autowired
    private TenantRepository tenantRepository;

    @GetMapping
    @Operation(summary = "Get all tenants", description = "Retrieve a list of all tenants")
    public List<Tenant> getAllTenants() {
        return tenantService.getAllTenants();
    }
    
    @GetMapping("/tenantId/{tenantId}")
    @Operation(summary = "Get tenant by tenantId", description = "Retrieve a tenant by its tenantId")
    public ResponseEntity<?> getTenantById(@PathVariable String tenantId) {
        try {
            Optional<Tenant> existingTenant = tenantRepository.findByTenantId(tenantId);
            if (existingTenant.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                Tenant tenant = tenantService.getTenantById(existingTenant.get().getId());
                return ResponseEntity.ok(tenant);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Tenant not present with ID: " + tenantId);
        }
    }
    
    @PostMapping("/addTenant")
    @Operation(summary = "Create a new tenant", description = "Create a new tenant with the provided details")
    public ResponseEntity<?> createTenant(@RequestBody Tenant tenant) {
        try {
            Optional<Tenant> existingTenant = tenantRepository.findByTenantId(tenant.getTenantId());
            if (existingTenant.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Tenant with tenantId " + tenant.getTenantId() + " already exists.");
            }
            
            Tenant createdTenant = tenantService.createTenant(tenant);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTenant);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create tenant: " + e.getMessage());
        }
    }

    @PutMapping("/updateTenant/{tenantId}")
    @Operation(summary = "Update tenant by tenantId", description = "Update an existing tenant's details by tenantId")
    public ResponseEntity<?> updateTenant(@PathVariable String tenantId, @RequestBody Tenant tenantDetails) {
        try {
            Optional<Tenant> tenantOptional = tenantRepository.findByTenantId(tenantId);
            if (tenantOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tenant not found with ID: " + tenantId);
            }

            Tenant updatedTenant = tenantService.updateTenant(tenantOptional.get().getId(), tenantDetails);
            return ResponseEntity.ok(updatedTenant);
        } catch (RuntimeException e) {
            String errorMessage = "Error updating tenant: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @DeleteMapping("/remove/{tenantId}")
    @Operation(summary = "Delete tenant by tenantId", description = "Delete an existing tenant by tenantId")
    public ResponseEntity<?> deleteTenant(@PathVariable String tenantId) {
        try {
            Optional<Tenant> tenantOptional = tenantRepository.findByTenantId(tenantId);
            if (tenantOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tenant not found with ID: " + tenantId);
            }
            
            tenantService.deleteTenant(tenantOptional.get().getId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete tenant: " + e.getMessage());
        }
    }
}
