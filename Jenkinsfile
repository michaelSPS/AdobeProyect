pipeline {
  agent any

  tools {
    allure 'Allure_2.13.9'
  }

  stage('Clean Docker System') {
        steps {
          sh 'docker container prune -f || true'
          sh 'docker image prune -f || true'
        }
      }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build Docker Image') {
      steps {
        // Construcción limpia (sin cache) para evitar errores con capas anteriores
        sh 'docker build --no-cache -t adobe-automation:latest .'
      }
    }

    stage('Run Tests in Docker') {
      steps {
        // Ejecuta los tests dentro del contenedor
        sh '''
          docker run --rm --shm-size=1g \
            -v $WORKSPACE:/usr/src/app \
            -w /usr/src/app \
            adobe-automation:latest \
            sh -c "mvn clean && mvn test"
        '''
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
            echo "$DOCKER_PASS" | docker login --username "$DOCKER_USER" --password-stdin

            docker tag adobe-automation:latest $DOCKER_USER/adobe-automation:$BUILD_NUMBER
            docker push $DOCKER_USER/adobe-automation:$BUILD_NUMBER

            docker logout
          '''
        }
      }
    }
  }

  post {
    always {
      junit 'target/surefire-reports/*.xml'

      allure([
        includeProperties: false,
        jdk: '',
        results: [[ path: 'target/allure-results' ]]
      ])
    }
  }
}
