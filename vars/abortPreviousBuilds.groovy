//import hudson.model.Result
//import hudson.model.Run
//import jenkins.model.CauseOfInterruption.UserInterruption

//def call() {
    // https://stackoverflow.com/a/49901413/4763512
  //  Run previousBuild = currentBuild.rawBuild.getPreviousBuildInProgress()
    //while (previousBuild != null) {
      //  if (previousBuild.isInProgress()) {
        //    def executor = previousBuild.getExecutor()
          //  if (executor != null) {
            //    echo ">> Aborting older build #${previousBuild.number}."
              //  executor.interrupt(Result.ABORTED, new UserInterruption(
                //    "Aborted by newer build #${currentBuild.number}."
                //))
            //}
        //}
        //previousBuild = previousBuild.getPreviousBuildInProgress()
    //}
//}

import hudson.model.Result
import jenkins.model.CauseOfInterruption

//iterate through current project runs
build.getProject()._getRuns().iterator().each{ run ->
  def exec = run.getExecutor()
  //if the run is not a current build and it has executor (running) then stop it
  if( run!=build && exec!=null ){
    //prepare the cause of interruption
    def cause = { "interrupted by build #${build.getId()}" as String } as CauseOfInterruption 
    exec.interrupt(Result.ABORTED, cause)
  }
}
