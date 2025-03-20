FROM jenkins/inbound-agent:latest

USER root
RUN cp /usr/share/zoneinfo/Asia/Kolkata /etc/localtime

# Install dependencies
RUN apt-get update && apt-get install -y software-properties-common apt-transport-https ca-certificates curl gnupg2 maven

# Add Docker’s official GPG key
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -

# Add Docker repository
RUN echo "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list

# Update package lists and install Docker CLI
RUN apt-get update && apt-get install -y docker-ce-cli

# Configure Docker to run as non-root user
RUN groupadd docker && usermod -aG docker jenkins

# Install Docker Compose
RUN curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose && \
    chmod +x /usr/local/bin/docker-compose

# Enable DinD
RUN mkdir -p /var/lib/docker
VOLUME /var/lib/docker
