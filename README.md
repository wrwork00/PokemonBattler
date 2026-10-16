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

## Skadeformel
Skadan beräknas med attackens grundskada och en multiplikator beroende på typmatchningen. Träffsäkerheten avgör om attacken träffar. En kritisk träff kan inträffa efter att attacken träffat.
- Neutral typmatchning: Grundskadan används utan elementbaserad multiplikator
- Elementfördel: En slumpmässig multiplikator mellan 1,0 och 1,5 används.
- Elementnackdel: En slumpmässig multiplikator mellan 0,5 och 1,0 används.
- Kritisk träff: Skadan multipliceras med 1,5.
- Miss: Ingen skada orsakas.

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

## Exempel: Pokédex
- Välj `2` för att lägga till en Pokémon.
- Ange namn, typ och max HP.
- Pokémonen läggs till i Pokédexen med standardattacker.
- Välj `5` för att spara Pokédexen.

## Exempel: Pokémon Battler 
- Välj `8` för att starta battle.
- Välj vilken av dina Pokémon som ska delta.
- Välj en attack under varje spelartur.
- Motståndaren attackerar om den fortfarande är vid liv.
- Striden avslutas när någon Pokémon besegras.
- Välj `9` för att visa sparade stridsresultat.

## OOP
Projektet är uppdelat i flera klasser med olika ansvarsområden för att göra koden lättare att förstå och underhålla.

- Pokemon representerar en Pokémon och dess egenskaper, exempelvis namn, typ, HP och attacker.
- Attack representerar en attack och dess egenskaper.
- DmgAttack hanterar hur en skadebaserad attack utförs.
- Type hanterar Pokémon-typer och typmatchningar.
- Battle hanterar stridens logik, turordning och attacker.
- BattleResult representerar resultatet av en strid.
- BattleResultStorage hanterar sparning och inläsning av stridsresultat.
- Pokedex hanterar spelarens Pokémon.
- Main hanterar programmets huvudmeny.

Klasserna har separata ansvarsområden för att undvika att all logik hamnar i en enda klass.

## Sparning av data
Pokédexen sparas i en JSON-fil med hjälp av Jackson. Stridsresultat sparas separat i battle-results.json, så att tidigare resultat kan visas efter att programmet startats om.

## Begränsningar

- Programmet är konsolbaserat och använder textmenyer.
- Vilda Pokémon och CPU attacker väljs slumpmässigt.
- Stridsresultat sparas som enskilda strider i battle-results.json