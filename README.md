# flight_planning

### Vajalikud:
Maven, Java, Node.js npm, PostgreSQL

### Backend - Java Spring Boot

Loo backend/.env fail (vt .env.example). Asenda POSTGRES_USER ja POSTGRES_PASSWORD väärtused enda kasutajanime ja parooliga.

```bash
cd backend
mvn spring-boot:run
```
Backend: http://localhost:8080/

** application.properties failis on andmebaas: create-drop testimise eesmärgil. Seega uuesti käivitamisel tehtud muudatused ei ole salvestatud.

### Frontend - Vue.js

```bash
cd frontend
npm install
npm run dev 
```
Frontend: http://localhost:5173/ 


# Märkmed
1. Tööks kulunud aeg: ≈ 3 tööpäeva
2. Mis oli keeruline: Istekohtade loogika paika panemine (nt mitu rida ma arvesse võtan, kas seda peaks olema võimalik muuta, nende soovitamine).
3. Probleemide lahendamisel kasutatud abi: Üldiselt kasutasin Tartu Ülikooli ettevõtte süsteemide integreerimises õpitud ülesehitust(https://courses.cs.ut.ee/2025/esi/spring), Google ning ka Claudet FlightView.vue osas (istekohad).  
4. Lahendamata jäänud probleemid: 
    - Võimalik, et istekohtade soovitamine ei ole päris nii nagu eeldatud. Kasutasin filtrites valitud kohtade soovitamise näitamiseks roosat värvi (mitte, et automaatselt valib need kohad sinu eest). Mulle kasutajakogemuse poolest tundus, see natukene selgem lahendus juhuks kui näiteks istekohti ei ole sobivaid. 
5. Eeldused mida kasutasin: 
    - Lennukis on 6 rida istekohti ja need on vahekäiguga eraldatud nö kaheks tulbaks. Ridade arvu saab vahetada failis (config/DataSeeder.java)
    - Kõik on turistiklass. 
    - Rohkem jalaruumi on random boolean hetkel. Kuigi loogiliselt oleks rohkem jalaruumi väljapääsu ligidal, siis tundus, et need oleksid muidu justkui samad. Ehk siis, et neid eristada jätsin random
    - Lähedal väljapääsule on : 1 rida, keskmine rida ja viimane rida. Ehk siis eeldan, et need on kohad kus asub väljapääs.

## Muu
Frontendis kasutatud Vue.js ja Tailwind css. UI komponendid on siit: https://merakiui.com/  

