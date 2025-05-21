pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps {
        // Clona del mismo repo y branch que configuraste en Jenkins
        checkout scm
      }
    }

    stage('Build & Test') {
      steps {
        // Replica tu comando Maven via Docker
        sh 'docker build -t adobe-automation:latest .'
        sh 'docker run --rm --shm-size=1g adobe-automation:latest'
      }
    }

    stage('Publish Docker Image') {
      when { branch 'main' }
      steps {
        withCredentials([usernamePassword(
          credentialsId: 'dockerhub',
          usernameVariable: 'DOCKER_USER',
          passwordVariable: 'DOCKER_PASS'
        )]) {
          sh """
            echo "$DOCKER_PASS" | docker login --username "$DOCKER_USER" --password-stdin
            docker tag adobe-automation:latest michaelSPS/adobe-automation:${BUILD_NUMBER}
            docker push michaelSPS/adobe-automation:${BUILD_NUMBER}
          """
        }
      }
    }
  }

  post {
    always {
      // Publícalo para que Jenkins muestre los reportes JUnit
      junit 'target/surefire-reports/*.xml'
    }
  }
}
