package auas.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import auas.dont_touch.database.Database;
import auas.read_only.domain.Blatt;

@Service
public class BlattService {

	private Database database;

	@Inject
	public BlattService(Database database) {
		this.database = database;
	}

	List<Blatt> vorhergehendeBlaetter(int upto) {
		return IntStream.range(0, upto)
				.mapToObj(this::getBlatt)
				.collect(Collectors.toList());
	}

	public Blatt getBlatt(int id) {
		return database.getBlatt(id);
	}

	public List<Blatt> getAllBlaetter() {
		return database.getBlaetter();
	}

	public void save(Blatt blatt) {
		database.save(blatt);
	}
}
