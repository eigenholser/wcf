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

import com.zyx2.practice.wcf.model.Phone;
import com.zyx2.practice.wcf.repository.PhoneRepository;

@Path("/phones")
public class PhoneEndpoints {
	
	@Inject
	private PhoneRepository phoneRepo;
	
	@GET
	@Path("/{id : \\d+}")
	@Produces(APPLICATION_JSON)
	public Response getPhone(@PathParam("id") Long id) {
		Phone phone = phoneRepo.find(id);
		return Response.ok(phone).build();
	}
	
	@GET
	@Produces(APPLICATION_JSON)
	public Response getPhones() {
		List<Phone> phones = phoneRepo.findAllPhones();
		return Response.ok(phones).build();
	}
	
	@POST
	@Consumes(APPLICATION_JSON)
	@Transactional(value = TxType.REQUIRED, rollbackOn = Throwable.class)
	public Response createPhone(Phone phone, @Context UriInfo uriInfo) {
		phone = phoneRepo.create(phone);
		URI createdUri = uriInfo.getBaseUriBuilder().path(phone.getEmployeeId().toString()).build();
		return Response.created(createdUri).build();
	}
}
