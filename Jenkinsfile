pipeline {

  agent any

    stages {    
        stage('git clone') {
           steps {
             git 'https://github.com/gsd1998/spring-security.git'
           }  
        }
        stage('mvn build') {
           steps {
             bat 'mvn clean package'
           }      
        }      
   }
} 
