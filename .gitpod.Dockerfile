FROM gitpod/workspace-full

USER gitpod

# Install Java 21 via SDKMAN and set it as default
RUN bash -c "source /home/gitpod/.sdkman/bin/sdkman-init.sh && \
    sdk install java 21-open && \
    sdk default java 21-open && \
    sdk use java 21-open"

ENV JAVA_HOME=/home/gitpod/.sdkman/candidates/java/current
ENV PATH=$JAVA_HOME/bin:$PATH
