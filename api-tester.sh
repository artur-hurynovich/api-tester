status=$(systemctl is-active mysql.service)

if [[ ${status} == 'active' ]]; then
    echo "Mysql is running... You should stop mysql service first (run 'sudo service mysql stop')"
    exit 1
else
    echo "Executing build and run tasks..."
fi

mvn clean install -DskipTests

cd docker || echo "Directory 'docker' not found"

function stop {
    echo ""
    echo "************************* REMOVING DOCKER CONTAINERS *************************"
    docker-compose down
    echo "************************* DOCKER CONTAINERS REMOVED *************************"
}

echo "************************* STARTING DOCKER *************************"
docker-compose up --build
