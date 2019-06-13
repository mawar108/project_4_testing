package auas.read_only.domain;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Blatt {

	private final String name;
	private final int id;

	private List<Abgabe> abgaben = new ArrayList<>();

	public Blatt(int id, List<Abgabe> abgaben) {
		this.id = id;
		this.name = "Blatt " + id;
		this.abgaben = abgaben;
	}

	public void addAbgabe(Abgabe abgabe) {
		abgaben.add(abgabe);
	}

	public String getName() {
		return name;
	}

	public int anzahlAllerAbgaben() {
		return abgaben.size();
	}

	public long anzahlZugeordneteAbgaben() {
		return abgaben.stream().filter(Abgabe::isZugeordnet).count();
	}

	private static <T> Predicate<T> not(Predicate<T> p) {
		return o -> !p.test(o);
	}

	public List<Abgabe> getUnzugeordneteAbgaben() {
		return abgaben.stream().filter(not(Abgabe::isZugeordnet)).collect(toList());
	}

	public List<Abgabe> getZugeordneteAbgaben() {
		return abgaben.stream().filter(Abgabe::isZugeordnet).collect(toList());
	}

	public int getId() {
		return id;
	}

	public void abgabeZuordnen(Abgabe abgabe, Korrektor korrektor) {
		abgabe.zuordnen(korrektor);
	}

	public List<Abgabe> getAlleAbgaben() {
		return abgaben;
	}

}
