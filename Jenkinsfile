// Check If Jenkins Job Was Started By A Timer
@NonCPS
def isJobStartedByTimer() {
    def startedByTimer = false

    try {
        def buildCauses = "${currentBuild.buildCauses}"
        if (buildCauses != null) {
            if (buildCauses.contains("Started by timer")) {
                startedByTimer = true
            }
            else {
              echo "NOT A CRON Timer Job"
            }
        }
    } catch(theError) {
        echo "Error getting build cause"
    }

    return startedByTimer
}

def mavenDeployCustom(cronBranch, nextTag) {
  // CRON Job To Be Run Daily At 19.30 Hour (UTC)
  String cronString = BRANCH_NAME == cronBranch ? "30 19 * * *" : ""
  def startedByTimer = false

  properties([pipelineTriggers([cron(cronString)])])

  stage('verify') {
    sh 'mvn --no-transfer-progress -Denv=jenkins -T 4C clean verify'
    notifySuccess "Verified ${nextTag}"
  }
  stage('deploy') {
    startedByTimer = isJobStartedByTimer()

    println "Job Timer Status - ${startedByTimer}"
    println "Job Branch Name  - ${env.BRANCH_NAME}"

    if (env.BRANCH_NAME.startsWith('master')) {
      tagIt(nextTag)

      if ( startedByTimer ) {
        sh 'mvn -DsuiteFileName=Testng-Nightly.xml -Ddeploy=true -Denv=jenkins --no-transfer-progress -T 4C bounds:update deploy'
        notifySuccess "CRON Trigger Built ${nextTag}"
      }
      else {
        sh 'mvn -DsuiteFileName=Testng-Nightly.xml -Ddeploy=true -Denv=jenkins --no-transfer-progress -T 4C bounds:update deploy'
        notifySuccess "GIT WebHook Built ${nextTag}"
      }
    }
    else {
      notifySuccess "Verified ${nextTag}"
    }
  }
}

olsTestsuiteSuperLongBuildLargeImageForgeDinD() {
  def cronBranch = 'master-nightly-extentreport'
  def nextTag = mavenPrepareVersion()
  mavenDeployCustom(cronBranch, nextTag)
}
