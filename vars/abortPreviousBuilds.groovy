import hudson.model.Result
import hudson.model.Run
import jenkins.model.CauseOfInterruption.UserInterruption

def call() {
    // https://stackoverflow.com/a/49901413/4763512
  Run previousBuild = currentBuild.rawBuild.getPreviousBuildInProgress()
   while (previousBuild != null) {
      if (previousBuild.isInProgress()) {
            def executor = previousBuild.getExecutor()
            if (executor != null) {
                echo ">> Aborting older build #${previousBuild.number}."
                executor.interrupt(Result.ABORTED, new UserInterruption(
                    "Aborted by newer build #${currentBuild.number}."
                ))
            }
        }
        previousBuild = previousBuild.getPreviousBuildInProgress()
    }
}


