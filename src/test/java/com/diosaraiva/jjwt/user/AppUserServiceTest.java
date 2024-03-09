package com.diosaraiva.jjwt.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.diosaraiva.jjwt.entity.AppUser;
import com.diosaraiva.jjwt.repository.AppUserRepository;
import com.diosaraiva.jjwt.service.impl.AppUserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest
{
	@InjectMocks
	AppUserServiceImpl appUserService;

	@Mock
	AppUserRepository appUserRepository;

	List<AppUser> listAppUser;

	@BeforeEach
	public void init() {

		listAppUser = new ArrayList<>();

		listAppUser.add(AppUser.builder()
				.build());

		listAppUser.add(AppUser.builder()
				.build());
	}

	@Test
	public void testAddAppUser() {

		when(appUserRepository.save(any(AppUser.class))).thenReturn(
				listAppUser.get(0));

		AppUser response = appUserService.addAppUser(
				listAppUser.get(0));

		assertThat(response.equals(listAppUser.get(0)));
	}

	@Test
	public void testGetAllAppUsers() {

		when(appUserRepository.findAll()).thenReturn(listAppUser);

		List<AppUser> response = appUserService.getAllAppUsers();

		assertThat(response.size()).isEqualTo(2);
	}
}