pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/DianaLBS/out.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
                sh 'mvn verify'
        }

        stage('Build Docker image') {
            steps {
                sh 'docker build -t outcome-curr-mgmt:latest .'
            }
        }

        stage('Deploy Docker image') {
            steps {
                sh 'docker run -d --name outcome-curr-mgmt -p 9092:9092 outcome-curr-mgmt:latest'
            }
        }

        stage('Reports') {
            steps {
                  archiveArtifacts artifacts: 'outcome-curr-mgmt-coverage/target/site/jacoco-aggregate/**/*', allowEmptyArchive: true
            }
        }
    }

    post {
        always {
            echo "Limpieza del entorno..."
            sh 'docker stop outcome-curr-mgmt || true'
            sh 'docker rm outcome-curr-mgmt || true'
        }
    }
}
