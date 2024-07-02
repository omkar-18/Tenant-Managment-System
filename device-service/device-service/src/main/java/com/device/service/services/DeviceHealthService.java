package com.device.service.services;

import java.util.List;

import com.device.service.entity.DeviceHealth;

public interface DeviceHealthService {

	List<DeviceHealth> getHealthDataByDeviceId(String deviceId);

	DeviceHealth addDeviceHealthData(DeviceHealth deviceHealth);

	DeviceHealth updateDeviceHealthData(Long id, DeviceHealth deviceHealthDetails);

	void deleteDeviceHealthData(Long id);

}
