package auas.dont_touch.database;

import java.util.List;
import java.util.stream.Collectors;

import auas.read_only.domain.Abgabe;
import auas.read_only.domain.Blatt;

class BlattEntity {

	private final int id;
	private List<AbgabeEntity> abgaben;

	public BlattEntity(int id) {
		this.id = id;
	}

	public BlattEntity(Blatt blatt) {
		this.id = blatt.getId();
		List<Abgabe> alleAbgaben = blatt.getAlleAbgaben();
		this.abgaben = alleAbgaben.stream().map(AbgabeEntity::new).collect(Collectors.toList());
	}

	void setAbgaben(List<AbgabeEntity> abgaben) {
		this.abgaben = abgaben;
	}

	int getId() {
		return id;
	}

	List<AbgabeEntity> getAbgaben() {
		return abgaben;
	}
}
