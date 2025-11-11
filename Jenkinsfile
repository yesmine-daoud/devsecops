pipeline {
    agent any

    environment {
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        MAVEN_HOME = "/usr/share/maven"
        BACKEND_PORT = "9090"
        GIT_CREDENTIALS = "yasmine"
        REPO_URL = "https://github.com/yesmine-daoud/devsecops.git"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Cloning repo..."
                git url: "${REPO_URL}", branch: 'main', credentialsId: "${GIT_CREDENTIALS}"
            }
        }

        stage('Build') {
            steps {
                echo "Building Maven project..."
                sh 'mvn clean package'
            }
        }

        stage('Run Backend') {
            steps {
                echo "Starting backend on port ${BACKEND_PORT}..."
                sh "nohup mvn exec:java -Dexec.mainClass='com.example.App' &"
                sh 'sleep 5'
            }
        }

        stage('Test API') {
            steps {
                echo "Testing backend endpoints..."
                sh "curl -f http://localhost:${BACKEND_PORT}/ || exit 1"
                sh "curl -f http://localhost:${BACKEND_PORT}/ping || exit 1"
            }
        }

        stage('Security Scan') {
            steps {
                echo "Running Trivy scan..."
                sh "trivy fs target/*.jar"
            }
        }

        stage('Archive') {
            steps {
                echo "Archiving JAR..."
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        always {
            echo "Stopping backend..."
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
