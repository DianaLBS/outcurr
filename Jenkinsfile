pipeline {
    agent any
    stages {

        stage('Build Docker image') {
            steps {
                sh 'docker build -t outcome-curr-mgmt:latest -f Dockerfile .'

            }
        }

        stage('Deploy Docker image') {
            steps {
                sh 'docker run -d --name outcome-curr-mgmt -p 9092:9092 outcome-curr-mgmt:latest'
            }
        }


        stage('Test') {
            steps {
                sh 'mvn test'
                sh 'mvn verify'
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
