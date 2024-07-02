package com.device.service.serviceImpl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.device.service.entity.DeviceHealth;
import com.device.service.repository.DeviceHealthRepository;
import com.device.service.services.DeviceHealthService;

import java.util.List;

@Service
public class DeviceHealthServiceImpl implements DeviceHealthService {
    @Autowired
    private DeviceHealthRepository deviceHealthRepository;

    @Override
    public List<DeviceHealth> getHealthDataByDeviceId(String deviceId) {
        return deviceHealthRepository.findByDeviceId(deviceId);
    }

    @Override
    public DeviceHealth addDeviceHealthData(DeviceHealth deviceHealth) {
        return deviceHealthRepository.save(deviceHealth);
    }

    @Override
    public DeviceHealth updateDeviceHealthData(Long id, DeviceHealth deviceHealthDetails) {
        DeviceHealth deviceHealth = deviceHealthRepository.findById(id).orElse(null);
        if (deviceHealth != null) {
            deviceHealth.setMemoryUsage(deviceHealthDetails.getMemoryUsage());
            deviceHealth.setHddUtilization(deviceHealthDetails.getHddUtilization());
            deviceHealth.setCpuDetails(deviceHealthDetails.getCpuDetails());
            deviceHealth.setDeviceModel(deviceHealthDetails.getDeviceModel());
            return deviceHealthRepository.save(deviceHealth);
        }
        return null;
    }

    @Override
    public void deleteDeviceHealthData(Long id) {
        deviceHealthRepository.deleteById(id);
    }
}
