package auas.services;

import java.util.Deque;
import java.util.LinkedList;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import auas.dont_touch.database.Database;
import auas.read_only.domain.Korrektor;

@Service
public class KorrektorService {

	private Database database;

	@Inject
	public KorrektorService(Database database) {
		this.database = database;
	}

	public Deque<Korrektor> getAll() {
		LinkedList<Korrektor> result = new LinkedList<>();
		result.addAll(database.getKorrektoren());
		return result;
	}

}
