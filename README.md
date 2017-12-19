# ansible-jenkins
this repo installs jenkins and configures it with ansible

## prerequisites
* ansible installed
* ubuntu-machine to push jenkins-installation too

## installation
* ```git clone https://github.com/jakobalander/ansible-jenkins.git```
* ```ansible-vault create vault.yml``` create a vault to store your sensitive data in:
```
---
vault_ansible_ssh_user: <your_server_user>
vault_ansible_become_pass: <your_server_password>
vault_jenkins_admin_user: <your_jenkins_user>
vault_jenkins_admin_pwd: <your_jenkins_password>
```
to edit the vault-file in retrospect
run ```ansible-vault edit vault.yml```

## usage
run ansible ```ansible-playbook site.yml --ask-vault-pass```
