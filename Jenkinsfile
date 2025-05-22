pipeline {
  agent any

   tools {
      allure 'Allure_2.13.9'
    }

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

    stage('Publish Docker Image') {
      when { branch 'main' }
      steps {
        withCredentials([usernamePassword(
          credentialsId: 'dockerhub',
          usernameVariable: 'DOCKER_USER',
          passwordVariable: 'DOCKER_PASS'
        )]) {
          sh '''
            echo "$DOCKER_PASS" \
              | docker login --username "$DOCKER_USER" --password-stdin

            # Usa la variable para taggear y pushear
            docker tag adobe-automation:latest \
              $DOCKER_USER/adobe-automation:$BUILD_NUMBER

            docker push \
              $DOCKER_USER/adobe-automation:$BUILD_NUMBER

            docker logout
          '''
        }
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
