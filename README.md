# Nikita Rulevics Projektu darbs Nr.1.
Testu tēma: skolas testi.

Ja nevar izveidot savienojumu ar datubāzi, palīdzēs šis darbības protokols:
1.	Pārbaudiet JDK versiju, ko var atrast, noklikšķinot uz Tools > Options > Java > Java Shell. Tur redzēsiet savu JDK versiju. Lai programma darbotos pareizi, ir nepieciešama 17. versija JDK. To var lejupielādēt šeit: https://download.oracle.com/java/17/archive/jdk-17.0.10_windows-x64_bin.exe.
2.	Nospiediet CTRL + 5, lai atvērtu logu Services.
3.	Atrodiet cilni JavaDB, noklikšķiniet uz tās ar peles labo pogu. Tur apakšā redzēsiet pogu Properties redzēsiet logu Java DB parametru rediģēšanai. Pirmajā ailītē jāizvēlas mape ar Derby draiveri, otrajā ailītē jāizvēlas datubāzes atrašanās vieta. Pirmajā šūnā atlasiet db-derby-10.16.1.1.1.1-bin mapi no projekta mapes. Otrajā šūnā jāizvēlas db mape no projekta mapes.
4.	Atgriezieties pakalpojumos un atrodiet tur cilni Drivers, noklikšķinot uz bultiņas blakus tai, atvērsiet draiveru sarakstu. Mūs interesē tikai Java DB(Embedded) un Java DB(Network).
5.	Ar peles labo pogu noklikšķiniet uz Java DB(Embedded), parādīsies izvēlne, jums jānoklikšķina uz pēdējās pogas Customize. Jūs redzēsiet draiveru failu sarakstu. Noklikšķiniet uz add un atrodiet db-derby-10.16.1.1.1.1-bin\lib mapi projekta mapē. Atlasiet četrus failus, izmantojot CTRL: derbyoptionaltools.jar, derbytools.jar, derbyclient.jar, derbyshared.jar. Noklikšķiniet uz pogām Open > OK.
6.	Ar peles labo pogu noklikšķiniet uz Java DB(Network), atvērsies izvēlne, jums jānoklikšķina uz pēdējās pogas Customize. Jūs redzēsiet draiveru failu sarakstu. Nospiediet pogu pievienot un atrodiet db-derby-10.16.1.1.1.1-bin\lib mapi projekta mapē. Atlasiet divus failus, izmantojot CTRL: derbyoptionaltools.jar, derbytools.jar. Noklikšķiniet uz pogām Open > OK.
7. 	Tagad logā Services noklikšķiniet uz bultiņu blakus Java DB. Zemāk redzēsiet datubāzu sarakstu. Tajā atrodiet TestDB. Noklikšķiniet uz tās ar peles labo pogu un izvēlieties pogu Connect.
8. 	Jūs esat veiksmīgi izveidojis savienojumu ar datu bāzi un varat izmantot manu programmu! Ja jums vēl ir kādi jautājumi vai kaut kas nav izdevies, varat rakstīt uz programmas administrācijas e-pasta adresi, kas ir norādīta zemāk.
