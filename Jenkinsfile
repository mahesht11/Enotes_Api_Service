pipeline {
    agent any
    stages {
    stage('git repo & clean') {
    steps {
        bat "rmdir /s /q enotes_api"
        bat "git clone https://github.com/mahesht11/Enotes_Api_Service.git"
        bat "mvn clean -f enotes_api_service"
        }
    }
    stage('install'){
        steps {
        bat "mvn install -f enotes_api_service"
        }
    }
    stage('test'){
        steps{
        bat "mvn test -f enotes_api_service"
        }
    }
    stage('Sonarqube') {
        steps {
            withSonarQubeEnv('sonarqube') {
            bat "mvn clean package sonar:sonar"
            }
        }
    }
    stage('build docker image'){
        steps {
            script {
            bat " docker build -t 276983/Enotes_Api_Service-0.0.1-SNAPSHOT.jar"
            }
        }
    }
    stage('package'){
        steps{
        bat "mvn package -f enotes_api_service"
        }
    }

 }

}
