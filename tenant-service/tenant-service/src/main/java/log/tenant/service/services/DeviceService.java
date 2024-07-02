package log.tenant.service.services;

import java.util.List;

import log.tenant.service.entity.Device;

public interface DeviceService {

	Device getDeviceById(Long id);

	Device createDevice(Device device);

	List<Device> getAllDevices();

	Device updateDevice(Long id, Device deviceDetails);

	void deleteDevice(String deviceId);

}
