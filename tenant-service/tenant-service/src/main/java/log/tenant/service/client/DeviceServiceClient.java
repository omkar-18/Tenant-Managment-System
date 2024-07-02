package log.tenant.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import log.tenant.service.entity.DeviceHealth;

import java.util.List;

@FeignClient(name = "device-service", url = "http://localhost:8082")
public interface DeviceServiceClient {
	
    @GetMapping("/device-health/{deviceId}")
    List<DeviceHealth> getHealthDataByDeviceId(@PathVariable("deviceId") String deviceId);

    @PostMapping("/device-health")
    DeviceHealth addDeviceHealthData(@RequestBody DeviceHealth deviceHealth);

    @PutMapping("/device-health/{id}")
    DeviceHealth updateDeviceHealthData(@PathVariable("id") Long id, @RequestBody DeviceHealth deviceHealth);

    @DeleteMapping("/device-health/{id}")
    void deleteDeviceHealthData(@PathVariable("id") Long id);
}
