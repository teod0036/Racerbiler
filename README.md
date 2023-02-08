# Valg i forhold til sensorer:
## Sensor vinkel
Vi har valgt at gøre vinklen som sensorerne er spredt over mindre fordi vores bane er smallere end den originale. Ved at gøre vinklen mindre sørger vi for at der er færrer situationer hvor begge side sensorer er uden for banen.

# Valg i forhold til fitness:
## Basis fitness
Vi har valgt at give bilerne en basis fitness på 2000 for at differenchere dem med mange minuspoint fra dem med få minuspoint. vi har valgt ikke at lave fitness til en double der kan gå negativt fordi det ville gøre den besværlig at bruge i randomBil og nextGen koden.
## Retning
Ved at sørge for at alle bilerne kører i den samme retning er det lettere at tjekke om bilerne kører rundt om banen eller bare kører rundt om checkpointsne.
## Køre Af Banen
I stedet for bare at dræbe alle biler der har været uden for banen har vi valgt at give dem minus point i stedet. Dette hjælper med at differenchere de biler der kører meget uden for banen fra dem der bare kom til at snitte kanten et sted.
## Checkpoints
Vi har valgt at tilføje nogle chackpoint på banen sådan at bilerne hurtigt finder ud af at det er godt at røre det grønne og at de skal gøre det i den rigtige retning. Det hjælper dem også med at fortsætte rundt efter de har rørt grøn første gang. Hvis man kun har målstregen sker det nogle gange at de enten tager meget lang tid om at finde målet, eller hvis man starter dem sådan at de hurtigt krydser målstregen bliver de "tilfredse", fordi de har mange point hurtigt og derefterr kører de dårligt.
## Tid
Vi har valgt at beregne fitness ud fra mængden af checkpoints passeret og ikke hvor hurtigt bilerne kører rudt da vi kom frem til at dette gav et bedre resultat.

# Valg i forhold til UI:
## Generations Tæller
Vi har valgt at tilføje mængden af generationer der er passeret oppe i venstre hjørne da det giver brugeren et bedre overblik over hvor længe programmet har kørt og hvor hurtigt bilerne forbedrer sig.

# Andet:
## Mutation
Vi har valgt at mutationen enten tilføjer eller fjerner en tiendedel af variansen da vi mente at en mindre ekstrem mutation ville være bedre end en mutation der bare flipper fortegnet på vægten. Mutationsraten har vi valgt til at være 4% da det giver nogel fine resultater.