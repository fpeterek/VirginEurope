# VirginEurope

### Information system for a fictional airline, Virgin Europe, Virgin's venture into Europe.

---

Upon realizing the third LHR runway is too far away and slots are impossible to obtain at London's most busy airport,
Virgin Group decided to found a brand new airline based at Kobeřice International (iata: KXB, icao: LKXB). This airport,
currently in the phase of preparation, is set to be built by 2025. Construction will start as soon as I obtain Qatari or 
Saudi funding. 

### Build

```
./gradlew build
```

### Run

```
java -jar build/libs/VirginEurope-1.0.jar
```

or

```
echo "" | java -jar build/libs/VirginEurope-1.0.jar  # Program will execute immediately and won't wait for user input 
```

or

```
./gradlew run
```

### Run in Docker

```
docker build -t virgineurope .
docker run -ti virgineurope
```

I intend to provide a `docker-compose.yaml` later on.

### KXB Airport

Map of KXB:

![Kobeřice International Airport](/images/kxb.png)

---

The domain `virgineurope.com` is actually registered by Virgin Enterprises Ltd., and the name might be trademarked,
so I might get sued for this. Anyway, if someone from Virgin is reading this, feel free to offer me a contract and let's
start the airline together.
