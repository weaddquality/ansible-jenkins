import jenkins.model.*

def jobName = "{{ jenkins_seed_job.name }}"

def configXml = """\
  <?xml version='1.0' encoding='UTF-8'?>
  <project>
    <actions/>
    <description></description>
    <keepDependencies>false</keepDependencies>
    <properties>
    </properties>
    <scm class="hudson.plugins.git.GitSCM" plugin="git@{{ plugin_git_version }}">
        <configVersion>2</configVersion>
        <userRemoteConfigs>
            <hudson.plugins.git.UserRemoteConfig>
                <url>{{ this_repository }}</url>
            </hudson.plugins.git.UserRemoteConfig>
        </userRemoteConfigs>
        <branches>
            <hudson.plugins.git.BranchSpec>
                <name>*/master</name>
            </hudson.plugins.git.BranchSpec>
        </branches>
        <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
        <submoduleCfg class="list"/>
        <extensions/>
    </scm>
    <canRoam>true</canRoam>
    <disabled>false</disabled>
    <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
    <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
    <authToken>seed</authToken>
    <triggers>
    <org.jvnet.hudson.plugins.triggers.startup.HudsonStartupTrigger plugin="startup-trigger-plugin@{{ plugin_startup_trigger }}">
      <spec></spec>
      <quietPeriod>0</quietPeriod>
      <runOnChoice>ON_CONNECT</runOnChoice>
    </org.jvnet.hudson.plugins.triggers.startup.HudsonStartupTrigger>
  </triggers>
    <concurrentBuild>false</concurrentBuild>
    <builders>
      <javaposse.jobdsl.plugin.ExecuteDslScripts plugin="job-dsl@{{ plugin_job_dsl_version }}">
        <targets>{{ jenkins_seed_job.dsl_script_path }}</targets>
        <usingScriptText>false</usingScriptText>
        <ignoreExisting>false</ignoreExisting>
        <removedJobAction>DELETE</removedJobAction>
        <removedViewAction>DELETE</removedViewAction>
        <lookupStrategy>JENKINS_ROOT</lookupStrategy>
        <additionalClasspath></additionalClasspath>
      </javaposse.jobdsl.plugin.ExecuteDslScripts>
    </builders>
    <publishers/>
    <buildWrappers/>
  </project>
""".stripIndent()


if (!Jenkins.instance.getItem(jobName)) {
    def xmlStream = new ByteArrayInputStream( configXml.getBytes() )
    try {
        Jenkins.instance.createProjectFromXML(jobName, xmlStream)
    } catch (ex) {
        println "ERROR: ${ex}"
        println configXml.stripIndent()
    }
}
