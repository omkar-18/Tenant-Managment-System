package log.tenant.service.controller;

import log.tenant.service.client.DeviceServiceClient;
import log.tenant.service.entity.DeviceHealth;
import log.tenant.service.entity.Device;
import log.tenant.service.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/device-health")
@Tag(name = "Device Health Management", description = "APIs for managing device health data")
public class DeviceHealthController {

	@Autowired
	private DeviceServiceClient deviceServiceClient; // Inject Feign Client for device-service

	@Autowired
	private DeviceRepository deviceRepository; // Inject DeviceRepository

	@PostMapping("/alldevice")
	@Operation(summary = "Add device health data", description = "Add health data for a device")
	public ResponseEntity<?> addDeviceHealthData(@RequestBody DeviceHealth deviceHealth) {
		// Check if deviceId exists in Device table
		Optional<Device> deviceOptional = deviceRepository.findByDeviceId(deviceHealth.getDeviceId());
		if (deviceOptional.isPresent()) {
			// Device exists, proceed to add health data
			DeviceHealth addedHealthData = deviceServiceClient.addDeviceHealthData(deviceHealth);
			return ResponseEntity.ok(addedHealthData);
		} else {
			// Device not found, return 404 Not Found
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Device not exist with ID: " + deviceHealth.getDeviceId());
		}
	}

	@GetMapping("get/{deviceId}")
	@Operation(summary = "Get health data by deviceId", description = "Retrieve health data by deviceId")
	public List<DeviceHealth> getHealthDataByDeviceId(@PathVariable String deviceId) {
		return deviceServiceClient.getHealthDataByDeviceId(deviceId);
	}

	@PutMapping("update/{deviceId}")
	@Operation(summary = "Update device health data", description = "Update health data for a device by its ID")
	public ResponseEntity<?> updateDeviceHealthData(@PathVariable String deviceId,
			@RequestBody DeviceHealth deviceHealth) {

		try {
			Device existingDevice = deviceRepository.findByDeviceId(deviceId)
					.orElseThrow(() -> new IllegalArgumentException("Device not found with deviceId: " + deviceId));

			DeviceHealth updatedDevice = deviceServiceClient.updateDeviceHealthData(existingDevice.getId(),
					deviceHealth);
			if (updatedDevice != null) {
				return ResponseEntity.ok(updatedDevice);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Device not found with Device ID: " + existingDevice.getDeviceId());
			}
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("" + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to update device: " + e.getMessage());
		}
	}

	@DeleteMapping("remove/{deviceId}")
	@Operation(summary = "Delete device health data", description = "Delete health data for a device by its ID")
	public ResponseEntity<?> deleteDeviceHealthData(@PathVariable String deviceId) {
		try {
			Device existingDevice = deviceRepository.findByDeviceId(deviceId)
					.orElseThrow(() -> new IllegalArgumentException("Device not foundif with deviceId: " + deviceId));
			
			deviceServiceClient.deleteDeviceHealthData(existingDevice.getId());
			
            return ResponseEntity.ok("Device deleted successfully");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Device not found with ID: " + deviceId);
        }
    }
}
