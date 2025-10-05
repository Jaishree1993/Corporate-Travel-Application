#!/bin/bash

echo "Installing Java 21..."

sudo apt update
sudo apt install -y openjdk-21-jdk

sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/java-21-openjdk-amd64/bin/java 121
sudo update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/java-21-openjdk-amd64/bin/javac 121
sudo update-alternatives --set java /usr/lib/jvm/java-21-openjdk-amd64/bin/java
sudo update-alternatives --set javac /usr/lib/jvm/java-21-openjdk-amd64/bin/javac

echo 'export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64' >> ~/.bashrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc

export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

echo "Java 21 setup complete."
java -version
