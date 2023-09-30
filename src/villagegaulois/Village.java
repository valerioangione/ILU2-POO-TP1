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
				if (etals[j].contientProduit(produit)) {
					res[i] = etals[j];
					i++;
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
		chaine.append(vendeur + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int i = marche.trouverEtalLibre();
		if (i == -1) {
			chaine.append("Le vendeur " + vendeur + " n'a pas trouver de place");
		} else {
			chaine.append("Le vendeur " + vendeur + " vend des " + produit + " à l'étal n°" + i + ".");
		}
		return chaine.toString();
	}

	public String rechercherVendeurProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] res = marche.trouverEtals(produit);
		if (res.length == 0) {
			chaine.append("Aucun vendeur ne propose de " + produit);
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < res.length; i++) {
				chaine.append("- " + res[i].getVendeur() + "\n");
			}
		}
		return chaine.toString();
	}
}