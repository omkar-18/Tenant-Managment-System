package log.tenant.service.entity;

import java.time.LocalDateTime;




public class DeviceHealth {
    private Long id;
    private String deviceId;
    private double memoryUsage;
    private double hddUtilization;
    private double cpuDetails;
    private String deviceModel;
    private LocalDateTime timestamp;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public double getMemoryUsage() {
		return memoryUsage;
	}
	public void setMemoryUsage(double memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
	public double getHddUtilization() {
		return hddUtilization;
	}
	public void setHddUtilization(double hddUtilization) {
		this.hddUtilization = hddUtilization;
	}
	public double getCpuDetails() {
		return cpuDetails;
	}
	public void setCpuDetails(double cpuDetails) {
		this.cpuDetails = cpuDetails;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
    
  
}

