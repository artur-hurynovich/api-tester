status=$(systemctl is-active mysql.service)

if [[ ${status} == 'active' ]]; then
  echo "Stopping mysql service..."
  sudo service mysql stop
  echo "Mysql service stopped"
fi

mvn clean install -DskipTests

cd docker || echo "Directory 'docker' not found"

echo "Removing docker containers..."
sudo docker-compose down
echo "Docker containers removed"

echo "Starting docker..."
sudo docker-compose up --build