package hashiGRP3.Logic.Aide;

import java.util.List;
import java.util.Optional;

import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;

public class IndiceResultat {
    private final String nomTechnique;
    private final String explication;
    private final NiveauDifficulte difficulte;
    private final Optional<Pont> pontSuggere;
    private final Optional<EtatDuPont> etatSuggere;
    private final boolean estInterdit;
    private final List<Ile> ilesImpliquees;
    private final List<Pont> pontsImpliques;

    public IndiceResultat(String nomTechnique, String explication, NiveauDifficulte difficulte,
                          Optional<Pont> pontSuggere, Optional<EtatDuPont> etatSuggere,
                          boolean estInterdit, List<Ile> ilesImpliquees, List<Pont> pontsImpliques) {
        this.nomTechnique = nomTechnique;
        this.explication = explication;
        this.difficulte = difficulte;
        this.pontSuggere = pontSuggere;
        this.etatSuggere = etatSuggere;
        this.estInterdit = estInterdit;
        this.ilesImpliquees = ilesImpliquees;
        this.pontsImpliques = pontsImpliques;
    }

    public String getNomTechnique() {
        return nomTechnique;
    }

    public String getExplication() {
        return explication;
    }

    public NiveauDifficulte getDifficulte() {
        return difficulte;
    }

    public Optional<Pont> getPontSuggere() {
        return pontSuggere;
    }

    public Optional<EtatDuPont> getEtatSuggere() {
        return etatSuggere;
    }

    public boolean isEstInterdit() {
        return estInterdit;
    }

    public List<Ile> getIlesImpliquees() {
        return ilesImpliquees;
    }

    public List<Pont> getPontsImpliques() {
        return pontsImpliques;
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
                ", ilesImpliquees=" + ilesImpliquees +
                ", pontsImpliques=" + pontsImpliques +
                '}';
    }
}