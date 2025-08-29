pipeline {
  agent any

  tools {
    allure 'Allure_2.13.9'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Clean Docker System') {
      steps {
        sh 'docker system prune -af'
      }
    }

    stage('Build Docker Image') {
      steps {
        sh 'docker build --no-cache -t adobe-automation:latest .'
      }
    }

    stage('Run Tests in Docker') {
      steps {
        sh '''
          docker run --rm --shm-size=1g \
            -v $WORKSPACE:/usr/src/app \
            -w /usr/src/app \
            adobe-automation:latest \
            mvn clean test
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
