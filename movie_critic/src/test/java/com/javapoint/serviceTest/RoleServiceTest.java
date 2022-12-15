package com.javapoint.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.javapoint.entities.Role;
import com.javapoint.repository.RoleRepo;
import com.javapoint.service.RoleService;



@RunWith(SpringRunner.class)

@SpringBootTest 
class RoleServiceTest {
	
	@Autowired 
  	private RoleService roleService;
	
	@MockBean 
  	private RoleRepo roleRepo;

	@Test
	void getRoleTest() {
		when(roleRepo.findAll()).thenReturn((List<Role>) Stream.of(new Role(1, "Admin"))
				.collect(Collectors.toList()));
		assertEquals(1,roleService.getAllRoleDetails().size());	
	}
	
	@Test
	public void saveRoleTest() {
		Role role= new Role(1, "Admin");
		when(roleRepo.save(role)).thenReturn(role);
		assertEquals(role, roleService.saveRoleDetails(role));
	}
	
	@Test
	public void getRoleByIdTest() {
		int id = 1;
		Role role = new Role(1, "Admin");
		when(roleRepo.findById(1)).thenReturn((role));
		assertEquals(role, roleService.getRoleDetailsById(id));
	}
	
	@Test
	public void deleteRoleTest() {
		Role role = new Role (1, "Admin");
		roleService.deleteRoleById(1);
		verify(roleRepo, times(0)).delete(role);
	}

}
