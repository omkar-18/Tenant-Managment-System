package log.tenant.service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import log.tenant.service.entity.Device;
import java.util.Optional;


public interface DeviceRepository extends JpaRepository<Device, Long> {
	Optional<Device> findByDeviceId(String deviceId);
	
	@Modifying
    @Query("DELETE FROM Device d WHERE d.deviceId = :deviceId")
    void deleteByDeviceId(String deviceId);
	
}