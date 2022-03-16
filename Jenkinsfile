pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        echo 'Building...'
      }
    }
    stage('Test') {
      steps {
        echo 'Testing...'
        sh "chmod +x gradlew"
        snykSecurity(
          snykInstallation: 'snyk@lastest',
          snykTokenId: 'admin-snyk-api-token',
          // place other parameters here
        )
      }
    }
    stage('Deploy') {
      steps {
        echo 'Deploying...'
      }
    }
  }
}
