pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/yesmine-daoud/devsecops.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                echo 'Build step - simulate build'
                // Tu peux appeler un script réel ici si nécessaire
            }
        }

        stage('Trivy Scan') {
            steps {
                echo 'Run Trivy scan'
                bat 'docker run --rm -v "%cd%":/src aquasec/trivy image alpine:latest'
            }
        }

        stage('OWASP Dependency-Check') {
            steps {
                echo 'Run OWASP Dependency-Check'
                bat 'docker run --rm -v "%cd%":/src -v "%cd%\\dependency-check-data":/report owasp/dependency-check --scan /src --format "HTML" --out /report'
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished!'
        }
    }
}

