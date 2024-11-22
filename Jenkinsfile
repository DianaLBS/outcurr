pipeline {
    agent { label 'docker-agent' }
    stages {
        stage('Run image') {
            steps {
                bat '''
                docker build -t outcome-curr-mgmt .
                docker run -d -p 9092:9092 outcome-curr-mgmt
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
                   publishHTML([
                       reportDir: 'outcome-curr-mgmt-coverage/target/site/jacocoaggregate',
                       reportFiles: 'index.html',
                       reportName: 'JaCoCo Coverage Report',
                       allowMissing: true
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
