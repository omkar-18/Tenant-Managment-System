package com.device.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.device.service.entity.DeviceHealth;
import com.device.service.services.DeviceHealthService;

import java.util.List;

@RestController
@RequestMapping("/device-health")
public class DeviceHealthController {
    @Autowired
    private DeviceHealthService deviceHealthService;

    @GetMapping("/{deviceId}")
    public List<DeviceHealth> getHealthDataByDeviceId(@PathVariable String deviceId) {
        return deviceHealthService.getHealthDataByDeviceId(deviceId);
    }

    @PostMapping
    public DeviceHealth addDeviceHealthData(@RequestBody DeviceHealth deviceHealth) {
        return deviceHealthService.addDeviceHealthData(deviceHealth);
    }

    @PutMapping("/{id}")
    public DeviceHealth updateDeviceHealthData(@PathVariable Long id, @RequestBody DeviceHealth deviceHealthDetails) {
        return deviceHealthService.updateDeviceHealthData(id, deviceHealthDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteDeviceHealthData(@PathVariable Long id) {
        deviceHealthService.deleteDeviceHealthData(id);
    }
}
