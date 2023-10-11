package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nombreEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nombreEtals);

	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public class Marche {
		private Etal[] etals;

		private Marche(int nombreEtals) {
			etals = new Etal[nombreEtals];
			for (int i = 0; i < nombreEtals; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() == false) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			Etal[] res = new Etal[etals.length];
			int i = 0;
			for (int j = 0; j < etals.length; j++) {
				if (etals[j].isEtalOccupe()) {
					if (etals[j].contientProduit(produit)) {
						res[i] = etals[j];
						i++;
					}
				}
			}
			return res;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}

		private String afficherMarche() {
			int nbEtalVide = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() == false) {
					nbEtalVide++;
				} else {
					etals[i].afficherEtal();
				}
			}
			return "�Il reste " + +nbEtalVide + " �tals " + "non utilis�s dans le march�.\n";
		}

	}

	public Etal rechercherEtal(Gaulois vendeur) {
		for (int i = 0; i < marche.etals.length; i++) {
			if (marche.etals[i] != null) {
				if (marche.etals[i].getVendeur() == vendeur) {
					return marche.etals[i];
				}
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int i = marche.trouverEtalLibre();
		if (i == -1) {
			chaine.append("Le vendeur " + vendeur.getNom() + " n'a pas trouver de place");
		} else {
			marche.etals[i].occuperEtal(vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + i + ".");
		}
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] res = marche.trouverEtals(produit);
		if (res.length == 0) {
			chaine.append("Aucun vendeur ne propose de " + produit);
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < res.length; i++) {
				if (res[i] != null) {
					chaine.append("- " + res[i].getVendeur().getNom() + "\n");
				}
			}
		}
		return chaine.toString();
	}

	public String partirVendeur(Gaulois vendeur) {
		for (int i = 0; i < marche.etals.length; i++) {
			if (marche.etals[i] != null) {
				if (marche.etals[i].getVendeur() == vendeur) {
					return marche.etals[i].libererEtal();
				}
			}
		}
		return null;

	}

	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		int vide = 0;
		chaine.append("Le marché du village " + getNom() + "possède plusieurs étals :\n");
		for (int i = 0; i < marche.etals.length; i++) {
			if (marche.etals[i].isEtalOccupe()) {
				chaine.append(marche.etals[i].getVendeur().getNom() + " vend " + marche.etals[i].getQuantité() + " "
						+ marche.etals[i].getProduit() + "\n");
			} else {
				vide++;
			}
		}
		chaine.append("Il reste " + vide + " étals non  utilisés dans le marché");
		return chaine.toString();
	}

}