
// RegistroDiCassa.java automatically generated from ASM2CODE
import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;
import java.util.Set;
import java.util.List;

import org.apache.commons.collections4.Bag;

abstract class RegistroDiCassaSig {
	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo astratto
	static class Pizza {
		static List<Pizza> elems = new ArrayList<>();
		static List<String> val = new ArrayList<>();

		Pizza(String a) {
			elems.add(this);
			val.add(a);
		}

		String toString(Pizza a) {
			if (elems.contains(a)) {
				return val.get(elems.lastIndexOf(a));
			} else
				return null;
		}

		Pizza get(String a) {
			if (val.contains(a)) {
				return elems.get(val.lastIndexOf(a));
			} else
				return null;
		}
	}

	List<String> Pizza_elemsList = new ArrayList<>();
	List<Pizza> Pizza_Class = new ArrayList<>();

	//Variabile di tipo Concreto o Enumerativo
	enum Stati {
		ATTENDI_ORDINAZIONI, SCEGLI_SE_AGGIUNGERE_PIZZA, CHIUSO, SCEGLI_TIPO_DI_PIZZA, PIZZASTANDARD_SELEZIONATA,
		ALTRAPIZZA_SELEZIONATA
	}

	//Variabile di tipo Concreto o Enumerativo
	enum Servizio {
		NEWORDINE, EXIT
	}

	//Variabile di tipo Concreto o Enumerativo
	enum AggiungiPizza {
		SI, NO
	}

	//Variabile di tipo Concreto o Enumerativo
	enum SelezioneTipoDiPizza {
		STANDARD, OTHER
	}

	//Metodi di supporto per l'implementazione delle funzioni controlled
	class Fun0Ctrl<D> {
		D oldValue;
		D newValue;

		void set(D d) {
			newValue = d;
		}

		D get() {
			return oldValue;
		}
	}

	static class FunNCtrl<D, C> {
		Map<D, C> oldValues = new HashMap<>();
		Map<D, C> newValues = new HashMap<>();

		void set(D d, C c) {
			newValues.put(d, c);
		}

		C get(D d) {
			return oldValues.get(d);
		}
	}

	//Metodi di supporto per l'implementazione delle funzioni non controlled
	class Fun0<D> {
		D value;

		void set(D d) {
			value = d;
		}

		D get() {
			return value;
		}
	}

	class FunN<D, C> {
		Map<D, C> values = new HashMap<>();

		void set(D d, C c) {
			values.put(d, c);
		}

		C get(D d) {
			return values.get(d);
		}
	}

	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	//Funzione di tipo Controlled
	Fun0Ctrl<Pizza> pizzaCorrente = new Fun0Ctrl<>();
	//Funzione di tipo Controlled
	Fun0Ctrl<Stati> statoCassa = new Fun0Ctrl<>();
	//Funzione di tipo Controlled
	Fun0Ctrl<Object> outMess = new Fun0Ctrl<>();

	//Funzione di tipo statico
	abstract Integer getPrezzo(Pizza param0_getPrezzo);

	//Funzione di tipo monitored
	Fun0<Servizio> servizioSelezionato = new Fun0<>();
	//Funzione di tipo monitored
	Fun0<Pizza> pizzaInserita = new Fun0<>();
	//Funzione di tipo monitored
	Fun0<AggiungiPizza> sceltaDiAggiuntaPizza = new Fun0<>();
	//Funzione di tipo monitored
	Fun0<SelezioneTipoDiPizza> sceltaDelTipoPizza = new Fun0<>();
	//Funzione di tipo monitored
	Fun0<Integer> insertQuantita = new Fun0<>();
	//Funzione di tipo monitored
	Fun0<Integer> insertPrezzo = new Fun0<>();
	//Funzione di tipo statico
	static Pizza margherita;
	//Funzione di tipo statico
	static Pizza marinara;
	//Funzione di tipo statico
	static Pizza capricciosa;
	//Funzione di tipo Controlled
	Fun0Ctrl<Integer> totale = new Fun0Ctrl<>();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	abstract void r_aggiungiPizzaStandardAlTotale();

	abstract void r_aggiungiAlTotale();

	abstract void r_attendiOrdinazioni();

	abstract void r_scegliSeAggiungerePizza();

	abstract void r_scegliTipoDiPizza();

	abstract void r_pizzaStandardSelezionata();

	abstract void r_altraPizzaSelezionata();

	abstract void r_Main();
}

class RegistroDiCassa extends RegistroDiCassaSig {
	// Inizializzazione di funzioni e domini
	RegistroDiCassa() {
		//Definizione iniziale dei domini statici
		//Definizione iniziale dei domini dinamici
		//Definizione iniziale dei domini astratti con funzini statiche
		margherita = new Pizza("margherita");
		Pizza_elemsList.add("margherita");
		Pizza_Class.add(margherita);
		marinara = new Pizza("marinara");
		Pizza_elemsList.add("marinara");
		Pizza_Class.add(marinara);
		capricciosa = new Pizza("capricciosa");
		Pizza_elemsList.add("capricciosa");
		Pizza_Class.add(capricciosa);
		//Inizializzazione delle funzioni
		totale.oldValue = totale.newValue = 0;
		statoCassa.oldValue = statoCassa.newValue = Stati.ATTENDI_ORDINAZIONI;
	}

	// Definizione delle funzioni statiche
	Integer getPrezzo(Pizza _c) {
		if (_c == margherita)
			return 4;
		else if (_c == marinara)
			return 3;
		else if (_c == capricciosa)
			return 5;
		return null;
	}

	// Conversione delle regole ASM in metodi java
	@Override
	void r_aggiungiPizzaStandardAlTotale() {
		//{ //seq
		totale.set((totale.get() + (getPrezzo(pizzaCorrente.get()) * insertQuantita.get())));
		totale.oldValue = totale.newValue;
		fireUpdateSet();
		outMess.set("prezzo totale aggiornato");
		outMess.oldValue = outMess.newValue;
		fireUpdateSet();
		//} //endseq
	}

	@Override
	void r_aggiungiAlTotale() {
		//{ //seq
		totale.set((totale.get() + (insertQuantita.get() * insertPrezzo.get())));
		totale.oldValue = totale.newValue;
		fireUpdateSet();
		outMess.set("prezzo totale aggiornato");
		outMess.oldValue = outMess.newValue;
		fireUpdateSet();
		//} //endseq
	}

	@Override
	void r_attendiOrdinazioni() {
		if (Boolean.TRUE.equals((statoCassa.get() == Stati.ATTENDI_ORDINAZIONI))) {
			//{ //par
			if (Boolean.TRUE.equals((servizioSelezionato.get() == Servizio.EXIT))) {
				//{ //par
				statoCassa.set(Stati.CHIUSO);
				outMess.set("Registro di cassa chiuso!");
				//} //endpar
			}
			if (Boolean.TRUE.equals((servizioSelezionato.get() == Servizio.NEWORDINE))) {
				//{ //par
				totale.set(0);
				statoCassa.set(Stati.SCEGLI_SE_AGGIUNGERE_PIZZA);
				outMess.set("Scegli se aggiungere una nuova pizza");
				//} //endpar
			}
			//} //endpar
		}
	}

	@Override
	void r_scegliSeAggiungerePizza() {
		if (Boolean.TRUE.equals((statoCassa.get() == Stati.SCEGLI_SE_AGGIUNGERE_PIZZA))) {
			//{ //par
			if (Boolean.TRUE.equals((sceltaDiAggiuntaPizza.get() == AggiungiPizza.SI))) {
				//{ //par
				statoCassa.set(Stati.SCEGLI_TIPO_DI_PIZZA);
				outMess.set("Scegli il tipo di pizza desiderata:");
				//} //endpar
			}
			if (Boolean.TRUE.equals((sceltaDiAggiuntaPizza.get() == AggiungiPizza.NO))) {
				//{ //seq
				outMess.set("Il totale :");
				outMess.oldValue = outMess.newValue;
				fireUpdateSet();
				outMess.set(totale.get());
				outMess.oldValue = outMess.newValue;
				fireUpdateSet();
				statoCassa.set(Stati.ATTENDI_ORDINAZIONI);
				statoCassa.oldValue = statoCassa.newValue;
				fireUpdateSet();
				//} //endseq
			}
			//} //endpar
		}
	}

	@Override
	void r_scegliTipoDiPizza() {
		if (Boolean.TRUE.equals((statoCassa.get() == Stati.SCEGLI_TIPO_DI_PIZZA))) {
			//{ //par
			if (Boolean.TRUE.equals((sceltaDelTipoPizza.get() == SelezioneTipoDiPizza.STANDARD))) {
				//{ //par
				statoCassa.set(Stati.PIZZASTANDARD_SELEZIONATA);
				outMess.set("Inserisci il nome di una pizza dell'elenco:");
				//} //endpar
			}
			if (Boolean.TRUE.equals((sceltaDelTipoPizza.get() == SelezioneTipoDiPizza.OTHER))) {
				//{ //par
				statoCassa.set(Stati.ALTRAPIZZA_SELEZIONATA);
				outMess.set("Inserisci il nome di una nuova pizza:");
				//} //endpar
			}
			//} //endpar
		}
	}

	@Override
	void r_pizzaStandardSelezionata() {
		if (Boolean.TRUE.equals((statoCassa.get() == Stati.PIZZASTANDARD_SELEZIONATA))) {
			if (Boolean.TRUE.equals(
					Pizza.elems.stream().anyMatch(c -> c.toString(c).equals(pizzaInserita.get().toString(c))))) {
				//{ //seq
				pizzaCorrente.set(pizzaInserita.get());
				pizzaCorrente.oldValue = pizzaCorrente.newValue;
				fireUpdateSet();
				r_aggiungiPizzaStandardAlTotale();
				fireUpdateSet();
				statoCassa.set(Stati.SCEGLI_SE_AGGIUNGERE_PIZZA);
				statoCassa.oldValue = statoCassa.newValue;
				fireUpdateSet();
				//} //endseq
			} else {
				//{ //seq
				outMess.set("Questa pizza non  presente in elenco!");
				outMess.oldValue = outMess.newValue;
				fireUpdateSet();
				statoCassa.set(Stati.SCEGLI_SE_AGGIUNGERE_PIZZA);
				statoCassa.oldValue = statoCassa.newValue;
				fireUpdateSet();
				//} //endseq
			}
		}
	}

	@Override
	void r_altraPizzaSelezionata() {
		if (Boolean.TRUE.equals((statoCassa.get() == Stati.ALTRAPIZZA_SELEZIONATA))) {
			//{ //seq
			r_aggiungiAlTotale();
			fireUpdateSet();
			statoCassa.set(Stati.SCEGLI_SE_AGGIUNGERE_PIZZA);
			statoCassa.oldValue = statoCassa.newValue;
			fireUpdateSet();
			//} //endseq
		}
	}

	@Override
	void r_Main() {
		//{ //seq
		r_attendiOrdinazioni();
		fireUpdateSet();
		//{ //par
		r_scegliSeAggiungerePizza();
		r_scegliTipoDiPizza();
		r_pizzaStandardSelezionata();
		r_altraPizzaSelezionata();
		//} //endpar
		fireUpdateSet();
		//} //endseq
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
		// No controlled functions initialized with monitored ones have been found
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {
		pizzaCorrente.oldValue = pizzaCorrente.newValue;
		statoCassa.oldValue = statoCassa.newValue;
		outMess.oldValue = outMess.newValue;
		totale.oldValue = totale.newValue;
	}

	//Metodo per l'aggiornamento dell'asm
	void updateASM() {
		r_Main();
		fireUpdateSet();
		initControlledWithMonitored();
	}

	public static void main(String[] args) {
		// TODO: auto-generated main method by Asmeta2Java 
	}
}
