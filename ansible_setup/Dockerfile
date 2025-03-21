# Use Ubuntu as the base image
FROM ubuntu:latest

# Install required packages
RUN apt update && \
    apt install -y openssh-server python3 sudo && \
    mkdir /run/sshd && \
    echo 'root:password' | chpasswd && \
    sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config && \
    service ssh restart

# Expose SSH port
EXPOSE 22

# Start SSH service when the container runs
CMD ["/usr/sbin/sshd", "-D"]

