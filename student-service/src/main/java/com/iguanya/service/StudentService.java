package com.iguanya.service;

import com.iguanya.entity.Student;
import com.iguanya.feignclients.AddressFeignClient;
import com.iguanya.repository.StudentRepository;
import com.iguanya.request.CreateStudentRequest;
import com.iguanya.response.AddressResponse;
import com.iguanya.response.StudentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StudentService {

	Logger logger = LoggerFactory.getLogger(StudentService.class);

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	WebClient webClient;

	@Autowired
	AddressFeignClient addressFeignClient;

	public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {

		Student student = new Student();
		student.setFirstName(createStudentRequest.getFirstName());
		student.setLastName(createStudentRequest.getLastName());
		student.setEmail(createStudentRequest.getEmail());
		student.setAddressId(createStudentRequest.getAddressId());
		student = studentRepository.save(student);

		StudentResponse studentResponse = new StudentResponse(student);

		//studentResponse.setAddressResponse(getAddressById(student.getAddressId()));

		studentResponse.setAddressResponse(addressFeignClient.getById(student.getAddressId()));



		return studentResponse;
	}
	
	public StudentResponse getById (long id) {
		logger.info("Inside getById " + id);
		Student student = studentRepository.findById(id).get();
		StudentResponse studentResponse = new StudentResponse(student);
		//studentResponse.setAddressResponse(getAddressById(student.getAddressId()));

		studentResponse.setAddressResponse(addressFeignClient.getById(student.getAddressId()));

		return studentResponse;
	}

	public AddressResponse getAddressById(long addressId){
		Mono<AddressResponse> addressResponseMono = webClient.get().uri("/getById/" + addressId).retrieve()
				.bodyToMono(AddressResponse.class);

		return addressResponseMono.block();
	}
}
