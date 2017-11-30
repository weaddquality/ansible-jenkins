#!groovy
import hudson.security.*
import jenkins.model.*

def instance = Jenkins.getInstance()
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
def users = hudsonRealm.getAllUsers()
users_s = users.collect { it.toString() }

if ("{{ jenkins_admin_user }}" in users_s) {
    def user = hudson.model.User.get('{{ jenkins_admin_user }}');
    def password = hudson.security.HudsonPrivateSecurityRealm.Details.fromPlainPassword('{{ jenkins_admin_pwd }}')
    user.addProperty(password)
    user.save()
} else {
    hudsonRealm.createAccount('{{ jenkins_admin_user }}', '{{ jenkins_admin_pwd }}')
    instance.setSecurityRealm(hudsonRealm)
    def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
    instance.setAuthorizationStrategy(strategy)
    instance.save()
}
