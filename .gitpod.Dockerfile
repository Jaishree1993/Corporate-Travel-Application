FROM gitpod/workspace-full

USER gitpod

ENV JAVA_HOME=/home/gitpod/.sdkman/candidates/java/current
ENV PATH=$JAVA_HOME/bin:$PATH
ENV SDKMAN_AUTO_ANSWER=true

RUN bash -c "source /home/gitpod/.sdkman/bin/sdkman-init.sh && \
    sdk install java 21-open && \
    sdk default java 21-open"

RUN echo 'export JAVA_HOME=/home/gitpod/.sdkman/candidates/java/current' >> /home/gitpod/.bashrc && \
    echo 'export PATH=$JAVA_HOME/bin:$PATH' >> /home/gitpod/.bashrc
