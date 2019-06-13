package auas.read_only.domain;

import java.util.UUID;

public class Korrektor {

	private final String name;
	private final int stunden;
	private final UUID id;

	public Korrektor(UUID id, String name, int stunden) {
		this.id = id;
		this.name = name;
		this.stunden = stunden;
	}

	public String getName() {
		return name;
	}

	public int getStunden() {
		return stunden;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Korrektor) {
			Korrektor that = (Korrektor) obj;
			return this.id.equals(that.id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
