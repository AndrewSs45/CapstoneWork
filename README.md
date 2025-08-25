Este proyecto es un simulador de un ecosistema el cuál se encuentra desarrollado en el lenguaje de programación java. Este sigue un conjunto de reglas, para poder simular la vida de cada uno de los seres que conviven en ese ecosistema, representando así el crecimiento y su supervivecia en el entorno ecológico.

En el ecosistema hay arboles, animales, agua y aire, los cuales tienen sus reglas de supervivencia y reproducción que van de generación en generación, actualizando el estado de cada celda según las interacciones que este simulador tenga.

Gen: 0

💨 💨 💨 🌳 💨
💨 💨 💧 🐑 💨
💨 💨 🌳 🌳 💨
💨 💨 🐑 🐑 💨
💨 💨 💨 💨 💨
Gen: X

💨 💨 🐑 🐑 🌳
💨 💨 💧 🐑 🌳
💨 💧 🐑 🐑 🌳
💧 💧 🐑 🐑 🌳
💧 🐑 🐑 🌳 💨
En los elementos podemos contar con:

0 (Representa al vacío (Aire)) 💨

Este elemento representa al aire solo, significando en una casilla vacía en la cual puede nacer algo o puede pasar un animal por ahí.

1 (Representa a los árboles) 🌳

Son la fuente de alimentación y uno de los medios indiscutibles de reproducción que hay entre los animales

2 (Representa a los animales) 🐑

Los animales solo se pueden mover en las generaciones pares, pero además de eso si hay 2 animales, una celda vacía y agua y comida podemos reproducirlos

3 (Representa al agua) 💧

Este es el elemento más importante para la supervivencia de las especies que coexisten en el ecosistema, además de que es una constante, osea donde esté el agua ahí permanecerá, pero claro dado que si sus reglas se cumplen, esta puede expandirse.

Para finalizar para este proyecto me gustaría agradecer a Jhampo -> Por su video de youtube de como hizo el juego de la vida en java https://www.youtube.com/watch?v=WNfaLMjsqF8

Chemaclass -> Por su repositorio de github en el cuál también estaba otro juego de la vida en el cuál pude basarme en unas cositas https://github.com/Chemaclass/juego-de-la-vida/blob/master/src/juegovida/Juego.java

W3Schools -> Por sus explicaciones con relación a algunos temas que no tenía del todo claros en java https://www.w3schools.com/java/default.asp

El proyecto no es perfecto, pero lo iré mejorando a medida que pase el tiempo.
