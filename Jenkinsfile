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
                sh './mvnw clean install'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
                sh './mvnw jacoco:report'
                jacoco execPattern: '**/target/*.exec',
                       classPattern: '**/classes',
                       sourcePattern: '**/src/main/java',
                       exclusionPattern: '**/src/test/java'
            }
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
                archiveArtifacts artifacts: '**/target/site/jacoco/index.html', fingerprint: true
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
