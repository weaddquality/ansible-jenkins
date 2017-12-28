import jenkins.model.Jenkins

def scriptApproval = Jenkins.instance.getExtensionList('org.jenkinsci.plugins.scriptsecurity.scripts.ScriptApproval')[0]
def hashesToApprove = scriptApproval.pendingScripts.collect{ it.getHash() }
hashesToApprove.each {
    scriptApproval.approveScript(it)
}
