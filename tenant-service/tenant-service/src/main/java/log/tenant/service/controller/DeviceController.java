package log.tenant.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import log.tenant.service.entity.Device;
import log.tenant.service.repository.DeviceRepository;
import log.tenant.service.services.DeviceService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/devices")
@Tag(name = "Device Management", description = "APIs for managing devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    
    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/all")
    @Operation(summary = "Get all devices", description = "Retrieve a list of all devices")
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping("deviceId/{deviceId}")
    @Operation(summary = "Get device by deviceId", description = "Retrieve a device by its deviceId")
    public ResponseEntity<?> getDeviceById(@PathVariable String deviceId) {
        try {
            Device existingDevice = deviceRepository.findByDeviceId(deviceId)
                    .orElseThrow(() -> new IllegalArgumentException("Device is not present with deviceId: " + deviceId));
            
            Device device = deviceService.getDeviceById(existingDevice.getId());
            if (device != null) {
                return ResponseEntity.ok(device);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found with ID: " + deviceId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("" + e.getMessage());
        }
    }

    @PostMapping("/addDevice")
    @Operation(summary = "Create a new device", description = "Create a new device with the provided details")
    public ResponseEntity<?> createDevice(@RequestBody Device device) {
        try {
            Device createdDevice = deviceService.createDevice(device);
            return ResponseEntity.ok(createdDevice);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Device with this ID already exists: " + device.getDeviceId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create device: " + e.getMessage());
        }
    }

    @PutMapping("/updateDevice/{deviceId}")
    @Operation(summary = "Update device by deviceId", description = "Update an existing device's details by deviceId")
    public ResponseEntity<?> updateDevice(@PathVariable String deviceId, @RequestBody Device deviceDetails) {
        try {
            Device existingDevice = deviceRepository.findByDeviceId(deviceId)
                    .orElseThrow(() -> new IllegalArgumentException("Device not found with deviceId: " + deviceId));
            
            Device updatedDevice = deviceService.updateDevice(existingDevice.getId(), deviceDetails);
            if (updatedDevice != null) {
                return ResponseEntity.ok(updatedDevice);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found with Device ID: " + existingDevice.getDeviceId());
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("" + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update device: " + e.getMessage());
        }
    }

    @DeleteMapping("/removeDevice/{deviceId}")
    @Operation(summary = "Delete device by deviceId", description = "Delete an existing device by deviceId")
    public ResponseEntity<String> deleteDevice(@PathVariable String deviceId) {
        try {
            deviceService.deleteDevice(deviceId);
            return ResponseEntity.ok("Device deleted successfully");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Device not found with ID: " + deviceId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .body("Device is present in tenant :" + deviceId);
        }
    }
}
