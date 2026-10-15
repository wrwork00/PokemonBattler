# Project Pokémon Battler
Pokémon Battler är ett konsolbaserat Java-program som bygger vidare på mitt tidigare Pokédex-projekt. Programmet låter användaren hantera sina Pokémon och delta i turbaserade strider mot vilda Pokémon

**Krav:** Java 21 & Maven

## How to - Starta programmet
- Klona programmet från GitHub
- Öppna projektet i IntelliJ IDEA och vänta tills projektet laddat klart.
- Kontrollera att Java 21 används i IntelliJ IDEA.
- Leta efter `public static void main(String[] args)`.
- Tryck på den gröna ▶-knappen bredvid `main` för att starta programmet. Alternativt Ctrl+F10 för att starta programmet.

## Pokédex Funktionsbeskrivning
- Lista Pokémon
- Söka efter Pokémon
- Filtrera Pokémon efter typ
- Sortera Pokémon efter namn, HP eller typ
- Visa statistik
- Lägga till Pokémon
- Redigera Pokémon
- Ta bort Pokémon
- Spara Pokédex
- Ladda Pokédex
- Återställa Pokédex
- Avsluta programmet

## Pokémon Battler Funktionsbeskrivning
- Välja en Pokémon från den egna Pokédexen.
- Möta en slumpmässigt vald vild Pokémon.
- Välja attacker under striden.
- Låta motståndaren välja attacker slumpmässigt.
- Hantera träffsäkerhet och skada.
- Avsluta striden när en Pokémon besegras.
- Visa en stridslogg efter avslutad strid.
- Spara och visa stridsresultat, inklusive vinster och förluster.
- Avbryta en pågående strid.

## Exempel på användning

Vid start visas Pokédex-menyn:

1. Pokémon
2. Add Pokémon
3. Edit Pokémon
4. Delete Pokémon
5. Save
6. Load
7. Reset
8. Start Battle
9. Battle Results
10. Exit

Exempel: Pokédex
- Välj `2` för att lägga till en Pokémon.
- Ange namn, typ och max HP.
- Pokémonen läggs till i Pokédexen med standardattacker.
- Välj `5` för att spara Pokédexen.

Exempel: Pokémon Battler 
- Välj `8` för att starta battle.
- Välj vilken av dina Pokémon som ska delta.
- Välj en attack under varje spelartur.
- Motståndaren attackerar om den fortfarande är vid liv.
- Striden avslutas när någon Pokémon besegras.
- Välj `9` för att visa sparade stridsresultat.