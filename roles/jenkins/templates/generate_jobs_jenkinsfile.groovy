def apps = [
        [project:"kesselborn",repo:"jenkinsfile"],
        [project:"jalogut",repo:"jenkinsfile-basic-sample"],
]

apps.each {  app ->
    repoUrl = 'https://github.com/' + app.project + '/' + app.repo + '.git'
    def content = readFileFromWorkspace('Jenkinsfile')
    content.replaceAll('--GIT_URL--',repoUrl)
    pipelineJob('Pipeline.' + app.repo.replaceAll('-','')) {
        definition {
            cps   {
                script(content)
                sandbox()
            }
        }
    }
}
