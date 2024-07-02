package com.device.service.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.device.service.entity.DeviceHealth;

import java.util.List;

public interface DeviceHealthRepository extends JpaRepository<DeviceHealth, Long> {
    List<DeviceHealth> findByDeviceId(String deviceId);
}
