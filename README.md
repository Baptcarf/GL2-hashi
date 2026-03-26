# GL2-hashi

## Contexte
Dans le cadre de notre troisième année de licence informatique à l’Université du Mans, et de la matière Génie Logiciel 2, 
nous sommes amenés à concevoir une application informatique en groupe de 7.  
Le projet porte sur le développement d’un système d’aide pour un jeu
japonais axé sur le puzzle et la logique nommé le “Hashi”. Ce jeu consiste à
relier des îles numérotées entre elles à l’aide de ponts en respectant des règles
et principes précis.

## Objectifs 
Ce projet a pour but d’appliquer les méthodes de génie logiciel vues en
cours, afin de structurer le projet et coordonner le groupe.
Les objectifs du logiciel sont de permettre à un joueur de terminer une
grille, même sans connaissance préalable du jeu grâce aux différents sys-
tèmes d’aide.s, d’apprendre les règles du Hashi en jouant, et de maîtriser les
différentes techniques afin de pouvoir finir une grille sans utiliser d’aide.

## Compilation
Vous devez en pré-requis avoir les outils/dépendances suivantes :
- Java => 21
- JavaFX => 21
- Maven

Il vous suffit ensuite de cloner la repository :
```
git clone https://github.com/Baptcarf/GL2-hashi.git
```

### Lancer
A partir de répertoire principale :
```
mvn javafx:run
```
### Générer la documentation
A partir de répertoire principale :
```
mvn javadoc:javadoc
```

### Générar un jar
A partir de répertoire principale :
```
mvn package
```
Noter que lorsque vous lancerez le .jar vous devez donner en paramètre le lien vers votre javafx :
```
java -jar GL2-Hashi.jar --module-path $PATH_TO_FX
```


