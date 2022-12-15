package com.javapoint.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.javapoint.entities.Actor;
import com.javapoint.repository.ActorRepo;
import com.javapoint.service.ActorService;

@SpringBootTest 
class ActorServiceTest {

	
	@Autowired 
  	private ActorService actorService;
	
	@MockBean 
  	private ActorRepo actorRepo;

	@Test
	void getActorTest() {
		when(actorRepo.findAll()).thenReturn((List<Actor>) Stream.of(new Actor(1, "Aman"))
				.collect(Collectors.toList()));
		assertEquals(1,actorService.getAllActorDetails().size());	
	}
	
	@Test
	public void saveActorTest() {
		Actor actor = new Actor (1, "Aman");
		when(actorRepo.save(actor)).thenReturn(actor);
		assertEquals(actor, actorService.saveActorDetails(actor));
	}
	
	@Test
	public void getActorByIdTest() {
		int id = 1;
		Actor actor = new Actor(1, "Aman");
		when(actorRepo.findById(1)).thenReturn((actor));
		assertEquals(actor, actorService.getActorDetailsById(id));
	}
	
	
	@Test
	public void deleteActorTest() {
		Actor actor = new Actor (1, "Aman");
		actorService.deleteActorById(1);
		verify(actorRepo, times(0)).delete(actor);
	}
}
