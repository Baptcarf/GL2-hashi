//Attribut au packet
package hashiGRP3.Logic.Aide;



//Imports
import java.util.Optional;

import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Pont;



/** Classe résultat d'une technique et les ponts concernés.*/
public class IndiceResultat {

	/** Nom de la technique */
	private final String nomTechnique;
    	/** Explication du principe de la technique */
	private final String explication;
    	/** Niveau de difficulte de la technique */
	private final NiveauDifficulte difficulte;
	private final Optional<Pont> pontSuggere;
	private final Optional<EtatDuPont> etatSuggere;
	private final boolean estInterdit;

	/** Constructeur de classe */
        public IndiceResultat(String nomTechnique, String explication, NiveauDifficulte difficulte,
                              Optional<Pont> pontSuggere, Optional<EtatDuPont> etatSuggere,
                              boolean estInterdit) {
		this.nomTechnique = nomTechnique;
		this.explication = explication;
		this.difficulte = difficulte;
		this.pontSuggere = pontSuggere;
		this.etatSuggere = etatSuggere;
		this.estInterdit = estInterdit;
    }

    /**
     * Getter sur l'attribut nomTechnique
     * @return Le nom de la technique.
     */
    public String getNomTechnique() {
        return nomTechnique;
    }

    /**
     * Getter sur l'attribut d'explication.
     * @return L'explication.
     */
    public String getExplication() {
        return explication;
    }

    /**
     * Getter sur l'attribut de difficulte. 
     * @return Le niveau de difficulte.
     */
    public NiveauDifficulte getDifficulte() {
        return difficulte;
    }

    /**
     * Getter sur l'attribut des ponts suggeres.
     * @return Une option
     */
    public Optional<Pont> getPontSuggere() {
        return pontSuggere;
    }

    /**
     * Getter sur l'attribut des etats suggeres.
     * @return Une option.
     */
    public Optional<EtatDuPont> getEtatSuggere() {
        return etatSuggere;
    }

    /**
     * Renvoie si l'usage de la technique est interdit.
     * @return Le booleen correspondant.
     */
    public boolean isEstInterdit() {
        return estInterdit;
    }

    @Override
    public String toString() {
        return "IndiceResultat{" +
                "nomTechnique='" + nomTechnique + '\'' +
                ", explication='" + explication + '\'' +
                ", difficulte=" + difficulte +
                ", pontSuggere=" + pontSuggere +
                ", etatSuggere=" + etatSuggere +
                ", estInterdit=" + estInterdit +
                '}';
    }
}
