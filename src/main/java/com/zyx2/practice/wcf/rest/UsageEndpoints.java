package com.zyx2.practice.wcf.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.zyx2.practice.wcf.model.Usage;
import com.zyx2.practice.wcf.repository.UsageRepository;

@Path("/usages")
public class UsageEndpoints {
	@Inject
	private UsageRepository usageRepo;
	
	@GET
	@Path("/{id : \\d+}")
	@Produces(APPLICATION_JSON)
	public Response getUsage(@PathParam("id") Long id) {
		Usage usage = usageRepo.find(id);
		return Response.ok(usage).build();
	}
	
	@GET
	@Produces(APPLICATION_JSON)
	public Response getUsages() {
		List<Usage> usages = usageRepo.findAll();
		return Response.ok(usages).build();
	}
	
	@GET
	@Path("/from/{months : \\d+}")
	@Produces(APPLICATION_JSON)
	public Response getUsagesByDate(@PathParam("months") Long months) {
		List<Usage> usages = usageRepo.findByDate(LocalDateTime.now().minusMonths(months).toLocalDate());
		return Response.ok(usages).build();
	}
	
	@GET
	@Path("/maxdate")
	@Produces(APPLICATION_JSON)
	public Response getMaxDate() {
		return Response.ok(usageRepo.maxDate()).build();
	}
	
	@POST
	@Consumes(APPLICATION_JSON)
	@Transactional(value = TxType.REQUIRED, rollbackOn = Throwable.class)
	public Response createUsage(Usage usage, @Context UriInfo uriInfo) {
		System.out.println(usage);
		usage = usageRepo.create(usage);
		URI createdUri = uriInfo.getBaseUriBuilder().path(usage.getEmployeeId().toString()).build();
		return Response.created(createdUri).build();
	}
}
