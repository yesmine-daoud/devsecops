pipeline {
    agent any

    environment {
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        MAVEN_HOME = "/usr/share/maven"
        BACKEND_PORT = "9090"
        GIT_CREDENTIALS = "yesmine" 
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/YOUR_USERNAME/YOUR_REPO.git', credentialsId: "${env.GIT_CREDENTIALS}"
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Run Backend') {
            steps {
                echo "Starting backend on port ${BACKEND_PORT}"
                sh "nohup mvn exec:java -Dexec.mainClass='com.example.App' &"
                sleep 5
            }
        }

        stage('Test API') {
            steps {
                echo "Testing backend API"
                sh "curl -f http://localhost:${BACKEND_PORT}/ || exit 1"
                sh "curl -f http://localhost:${BACKEND_PORT}/ping || exit 1"
            }
        }

        stage('Security Scan') {
            steps {
                echo "Running Trivy scan on target jar"
                sh "trivy fs target/*.jar"
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        always {
            echo "Stopping backend"
            sh "pkill -f 'com.example.App' || true"
        }
        success {
            echo "Pipeline succeeded!"
        }
        failure {
            echo "Pipeline failed!"
        }
    }
}
