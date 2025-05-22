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
        // Construye la imagen y ejecuta los tests dentro del contenedor
        sh 'docker build -t adobe-automation:latest .'
        sh 'docker run --rm --shm-size=1g adobe-automation:latest'
      }
    }
  }

  post {
    always {
      // Publica resultados JUnit
      junit 'target/surefire-reports/*.xml'

      // Genera y publica reporte Allure
      allure([
        includeProperties: false,
        jdk: '',
        results: [[ path: 'target/allure-results' ]]
      ])
    }
  }
}
