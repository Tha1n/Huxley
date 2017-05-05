node {
    stage ('Compilation') {
        build job: 'Huxley - Compile'
    }
    stage ('Test') {
        build job: 'Huxley - Test'
    }
    stage ('Inspection Sonar') {
        build job: 'Huxley - Qualify'
    }
    stage ('Installation') {
        build job: 'Huxley - Install'
    }
}
