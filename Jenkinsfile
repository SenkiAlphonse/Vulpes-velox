pipeline {
    agent any

    stages {
        stage('Test') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew --stacktrace test'
            }
        }
    }
}
