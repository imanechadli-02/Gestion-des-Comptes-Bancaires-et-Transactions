//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Model;

public abstract class Personne {
    protected String nom;
    protected String prenom;
    protected String email;
    protected String motDePasse;

    public Personne(String var1, String var2, String var3, String var4) {
        this.nom = var1;
        this.prenom = var2;
        this.email = var3;
        this.motDePasse = var4;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String var1) {
        this.nom = var1;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String var1) {
        this.prenom = var1;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String var1) {
        this.email = var1;
    }

    public String getMotDePasse() {
        return this.motDePasse;
    }

    public void setMotDePasse(String var1) {
        this.motDePasse = var1;
    }

    public String toString() {
        return "Nom: " + this.nom + ", Prénom: " + this.prenom + ", Email: " + this.email;
    }
}
