package com.pts.findInternship.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.r2s.findInternship.dto.MajorDTO;
import com.r2s.findInternship.entity.Major;
import com.r2s.findInternship.mapstructmapper.MapperMajor;
import com.r2s.findInternship.repository.MajorRepository;
import com.r2s.findInternship.service.impl.MajorServiceImpl;


@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class MajorServiceTest {
	@Mock
	private MajorRepository majorRepository;
	@Mock
	private MapperMajor mapperMajor;
	@InjectMocks 
	private MajorServiceImpl majorService;
	
	
	@Test
	void testFindIdMajor_WhenIdExisted_ReturnFaile()
	{
		Major major = new Major();
		major.setId(2);
		major.setName("oke");
		MajorDTO majorDTO = new MajorDTO();
		majorDTO.setId(3);
		majorDTO.setName("oke");
		Mockito.when(majorRepository.findById(3)).thenReturn(Optional.of(major));
		Mockito.when(mapperMajor.map(major)).thenReturn(majorDTO);
		MajorDTO entity = majorService.findById(3);
		assertEquals(entity, majorDTO);
	}

	
}
