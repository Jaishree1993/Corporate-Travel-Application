FROM gitpod/workspace-full

USER gitpod

# Set environment variables early
ENV JAVA_HOME=/home/gitpod/.sdkman/candidates/java/current
ENV PATH=$JAVA_HOME/bin:$PATH
ENV SDKMAN_AUTO_ANSWER=true

# Install Java 21 via SDKMAN and set it as default
RUN bash -c "source /home/gitpod/.sdkman/bin/sdkman-init.sh && \
    sdk install java 21-open && \
    sdk default java 21-open"

# Ensure all future shells use Java 21
RUN echo 'source "/home/gitpod/.sdkman/bin/sdkman-init.sh" && sdk use java 21-open' >> /home/gitpod/.bash_profile && \
    echo 'source "/home/gitpod/.sdkman/bin/sdkman-init.sh" && sdk use java 21-open' >> /home/gitpod/.profile && \
    echo 'export JAVA_HOME=/home/gitpod/.sdkman/candidates/java/current' >> /home/gitpod/.bashrc && \
    echo 'export PATH=$JAVA_HOME/bin:$PATH' >> /home/gitpod/.bashrc
