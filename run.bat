ECHO OFF

docker-compose down
ECHO =========================================
ECHO ========= BUILDING MODEL ================
ECHO =========================================
cd Config-Model-Api
call mvn clean install -DskipTests

ECHO =========================================
ECHO ========= BUILDING CONSUMER =============
ECHO =========================================
cd ../Consumer
call mvn clean install -DskipTests

ECHO =========================================
ECHO ========= BUILDING PRODUCER =============
ECHO =========================================
cd ../Producer
call mvn clean install -DskipTests

ECHO =========================================
ECHO ===== PROJECTS BUILT SUCCESSFULLY =======
ECHO ===== REBUILDING / RE-SETTING DOCKER IMAGES / CONTAINERS =======
ECHO =========================================
cd ..
docker-compose up -d --build
docker-compose logs -f
