package com.ft.api.util.buildinfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/build-info")
@Produces(MediaType.APPLICATION_JSON)
public class BuildInfoResource {

	private static final String DEFAULT_BUILD_INFO_PROPERTIES = "/build-info.properties";

	private final String propertiesFileName;
	
	public BuildInfoResource() {
		propertiesFileName = DEFAULT_BUILD_INFO_PROPERTIES;
	}
	
	public BuildInfoResource(String propertiesFileName) {
		this.propertiesFileName = propertiesFileName;
	}

	@GET
	public BuildInfo getBuildInfo() {

		try {
			Properties buildInfoProperties = PropertiesFileLoader.load(propertiesFileName);
			return new BuildInfo(buildInfoProperties);
		} catch(PropertiesFileLoaderException ex) {
			Response error = Response.serverError()
									 .entity(singleValueMap("message", ex.getMessage()))
									 .type(MediaType.APPLICATION_JSON)
									 .build();
			
			throw new WebApplicationException(error);
		}
	}

	private Map<String, String> singleValueMap(String key, String value) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(key, value);
		return map;
	}

}
