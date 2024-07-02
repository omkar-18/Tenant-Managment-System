package log.tenant.service.serviceImpl;

import log.tenant.service.entity.Device;
import log.tenant.service.repository.DeviceRepository;
import log.tenant.service.services.DeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Device getDeviceById(Long id) {
        return deviceRepository.findById(id).orElse(null);
    }

    @Override
    public Device createDevice(Device device) {
    	Optional<Device> device1=deviceRepository.findByDeviceId(device.getDeviceId());
    	 if (device1.isPresent()) {
    	        // Device with the same ID already exists, return an error message
    	        throw new IllegalArgumentException("Device with ID " + device1 + " already exists");
    	    } else {
    	        // Device with this ID does not exist, save the new device
    	        return deviceRepository.save(device);
    	    }
        
    }

    @Override
    public Device updateDevice(Long id, Device deviceDetails) {
        Device device = deviceRepository.findById(id).orElse(null);
        if (device != null) {
            device.setSerialNumber(deviceDetails.getSerialNumber());
            device.setStatus(deviceDetails.getStatus());
            return deviceRepository.save(device);
        }
        return null;
    }

    @Override
    public void deleteDevice(String deviceId) {
        try {
            deviceRepository.deleteByDeviceId(deviceId);
        } catch (EmptyResultDataAccessException ex) {
            // Handle the case where no device with the given ID was found
            throw new NoSuchElementException("Device not found with ID: " + deviceId);
        } catch (Exception e) {
            // Handle other unexpected exceptions
            throw new RuntimeException("Device is present in tenant : " + deviceId, e);
        }
    }
}
