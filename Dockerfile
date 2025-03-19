FROM jenkins/jenkins:lts

ENV JENKINS_HOME=/.jenkins

USER root

RUN apt-get update && apt-get install -y \
    git \
    maven \
    gradle \
    && rm -rf /var/lib/apt/lists/*

USER jenkins

RUN jenkins-plugin-cli --plugins git gradle maven-plugin

EXPOSE 8080
ENTRYPOINT ["/usr/bin/tini", "--", "/usr/local/bin/jenkins.sh"]


