package log.tenant.service.entity;

import javax.persistence.*;


@Entity

public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String deviceId;
    
    @Column(unique = true)
    private String serialNumber;
    
    private String status;

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

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
   
//    @ManyToOne
//    @JoinColumn(name = "tenant_id")
//    @JsonBackReference
//    private Tenant tenant;
    // Getters and Setters
    
    
}