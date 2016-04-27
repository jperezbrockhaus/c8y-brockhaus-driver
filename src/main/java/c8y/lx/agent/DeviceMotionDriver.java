package c8y.lx.agent;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.svenson.*;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.model.operation.OperationStatus;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.rest.representation.operation.OperationRepresentation;
import com.cumulocity.sdk.client.Platform;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import c8y.DeviceMotion;
import c8y.lx.driver.Driver;
import c8y.lx.driver.OperationExecutor;
import c8y.lx.driver.OpsUtil;

/**
 * A driver that executes a device motion operation
 *
 * Project: c8y-brockhaus-driver
 *
 * Copyright (c) by Brockhaus Group www.brockhaus-gruppe.de
 * 
 * @author jperez, Apr 12, 2016
 *
 */

public class DeviceMotionDriver implements Driver, OperationExecutor {
	
	private static Logger logger = LoggerFactory.getLogger("device motion driver");
	private GId gid;
	
	@Override
	public void initialize() throws Exception {}

	@Override
	public void initialize(Platform platform) throws Exception {}

	@Override
	public OperationExecutor[] getSupportedOperations() {
		return new OperationExecutor[]{this};
	}

	@Override
	public void initializeInventory(ManagedObjectRepresentation mo) {
		OpsUtil.addSupportedOperation(mo, supportedOperationType());
		
	}

	@Override
	public void discoverChildren(ManagedObjectRepresentation mo) {
		this.gid = mo.getId();
		
	}

	@Override
	public void start() {}

	@Override
	public String supportedOperationType() {
		return "c8y_DeviceMotion";
	}

	@Override
	public void execute(OperationRepresentation operation, boolean cleanup) throws Exception {
		if (!this.gid.equals(operation.getDeviceId())) {
			// Silently ignore the operation if it is not targeted to us,
			// another driver will (hopefully) care.
			return;
		}
		
		if (cleanup) {
			operation.setStatus(OperationStatus.SUCCESSFUL.toString());
		} 
		else {
			logger.info("Sending Device Motion Operation...");
			String json = JSON.defaultJSON().forValue(operation.getProperty("c8y_DeviceMotion"));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
			String dt = dateFormat.format(operation.getCreationTime());
		    JSONParser parser = new JSONParser();
		    DeviceMotion dm = parser.parse(DeviceMotion.class, json);
		    dm.setTime(dt);
		    json = JSON.defaultJSON().forValue(dm);
		    logger.info("Operation Message: " + "\n" + json);
			Client client = Client.create();

			WebResource webResource = client
					.resource("http://localhost:8080/sensorData/rest/command/sensorMessage");

			ClientResponse response = webResource.type("application/json")
						.post(ClientResponse.class, json);
			
			if (response.getStatus() != 204) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
			operation.setStatus(OperationStatus.SUCCESSFUL.toString());
			logger.info("Device Motion Operation Sent Successfully");
		}
	}
}