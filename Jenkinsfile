pipleine {
    agent any
    stages {
        stage(test) {
            sh 'chmod +x gradlew'
            sh './gradlew --stacktrace test'
        }
    }
}
