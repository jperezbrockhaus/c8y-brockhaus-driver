package c8y;

import java.io.Serializable;

import org.svenson.AbstractDynamicProperties;
import org.svenson.JSONProperty;

/**
 * An operation to move a device
 *
 * Project: c8y-brockhaus-driver
 *
 * Copyright (c) by Brockhaus Group www.brockhaus-gruppe.de
 * 
 * @author jperez, Apr 12, 2016
 *
 */

public class DeviceMotion extends AbstractDynamicProperties implements Serializable {
	private static final long serialVersionUID = 1L;
	private String sensorId;
	private String time;
	private String datatype;
	private Boolean value;
	
	@JSONProperty(ignoreIfNull = false)
	public String getSensorId() {
		return this.sensorId;
	}
	
	public void setSensorId(String param) {
		this.sensorId = param;
	}
	
	@JSONProperty(ignoreIfNull = true)
	public String getTime() {
		return this.time;
	}
	
	public void setTime(String param) {
		this.time = param;
	}
	
	@JSONProperty(ignoreIfNull = false)
	public String getDatatype() {
		return this.datatype;
	}
	
	public void setDatatype(String param) {
		this.datatype = param;
	}
	
	@JSONProperty(ignoreIfNull = false)
	public Boolean getValue() {
		return this.value;
	}
	
	public void setValue(Boolean param) {
		this.value = param;
	}
	
	@Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (sensorId != null ? sensorId.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (datatype != null ? datatype.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceMotion)) return false;

        DeviceMotion that = (DeviceMotion) o;

        if (sensorId != null ? !sensorId.equals(that.sensorId) : that.sensorId != null) return false;
        if (time != that.time) return false;
        if (datatype != that.datatype) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "sensorId = " + sensorId +
                ", time = '" + time + '\'' +
                ", datatype = '" + datatype + '\'' +
                ", value = " + value +
                '}';
    }
}