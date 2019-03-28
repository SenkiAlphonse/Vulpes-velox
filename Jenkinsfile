pipeline {
    agent any
    environment {
        registry = "foxyfox/vulpes"
        registryCredential = 'docker-technical-foxyfox'
        dockerImage = ''
        ENV_NAME = "kopako-vulpes-env"
        S3_BUCKET = "kopako-vulpes-bucket"
        APP_NAME = 'kopako-vulpes'
    }
    stages {
        stage('Preparation') {
            steps {
                sh 'chmod +x gradlew'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
        stage('Build') {
            steps {
                slackSend(
                        channel: '#fedexfox',
                        message: "${currentBuild.fullDisplayName} has started."
                )
                sh './gradlew bootJar'
            }
        }
        stage('Deploy docker image') {
            when {
                branch 'production'
            }
            steps {
                script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                        dockerImage.push('latest')
                    }
                }
                slackSend(
                        channel: '#fedexfox',
                        color: 'good',
                        message: "Docker image built and pushed to dockerhub; <https://hub.docker.com/r/foxyfox/vulpes/tags | Dockerhub>"
                )
            }
        }
        stage('Deploy to AWS') {
            when {
                branch 'production'
            }
            steps {
                withAWS(credentials: '04dc3c33-f243-4662-a597-20f7e2ddd77b', region: 'eu-west-3') {
                    sh 'aws s3api get-bucket-acl --bucket kopako-vulpes-bucket --region us-west-3'
                    sh 'aws s3 cp ./Dockerrun.aws.json \
                        s3://$S3_BUCKET/$BUILD_ID/Dockerrun.aws.json'
                    sh 'aws elasticbeanstalk create-application-version \
                        --application-name "$APP_NAME" \
                       --version-label vulpes-$BUILD_ID \
                       --source-bundle S3Bucket="$S3_BUCKET",S3Key="$BUILD_ID/Dockerrun.aws.json" \
                       --auto-create-application'
                    sh 'aws elasticbeanstalk update-environment \
                       --application-name "$APP_NAME" \
                       --environment-name $ENV_NAME \
                       --version-label vulpes-$BUILD_ID'
                }
                slackSend(
                        channel: '#fedexfox',
                        color: 'good',
                        message: "EBS updated with new image; <http://kopakop-vulpes-env.vbyegtzz7n.eu-north-1.elasticbeanstalk.com/ | [Link] >"
                )
            }
        }
    }
    post {
        success {
            slackSend(
                    channel: '#fedexfox',
                    color: 'good',
                    message: "${currentBuild.fullDisplayName} has succeeded. (<${env.BUILD_URL}|Open>)"
            )
        }
        failure {
            slackSend(
                    channel: '#fedexfox',
                    color: 'danger',
                    message: "${currentBuild.fullDisplayName} has failed. (<${env.BUILD_URL}|Open>)"
            )
        }
    }
}
