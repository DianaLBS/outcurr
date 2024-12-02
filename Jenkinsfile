pipeline {
    agent { label 'docker-agent' }
    stages {
        stage('Run image') {
            steps {
                bat '''
                docker build -t outcome-curr-mgmt .
                docker run -d -p 8088:8088 outcome-curr-mgmt
                '''
            }
        }
        stage('Run Tests') {
            steps {
                bat 'mvn clean test'
            }
        }
        stage('Report') {
            steps {
                bat 'mvn verify'
            }
            post {
                always {
                    publishHTML(target: [
                        reportDir: 'outcome-curr-mgmt-coverage/target/site/jacocoaggregate', // Directorio del reporte
                        reportFiles: 'index.html', // Archivo del reporte
                        reportName: 'JaCoCo Coverage Report', // Nombre del reporte
                        allowMissing: true, // Permitir que falte el reporte (por ejemplo, si las pruebas fallan)
                        keepAll: true, // Mantener todos los reportes
                        alwaysLinkToLastBuild: true // Enlazar siempre al último build
                    ])
                }
            }
        }
    }
    post {
        always {
            script {
                bat '''
                for /f "tokens=*" %%i in ('docker ps -a -q --filter "ancestor=outcome-curr-mgmt"') do docker rm %%i
                docker rmi outcome-curr-mgmt --force || true
                '''
            }
        }
        success {
            echo 'Pipeline executed successfully.'
        }
        failure {
            echo 'Pipeline execution failed.'
        }
    }
}
