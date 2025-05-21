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
  }

  post {
    always {
      // Publícalo para que Jenkins muestre los reportes JUnit
      junit 'target/surefire-reports/*.xml'
    }
  }
}
