set -x
set -e

mvn package
~/.jdks/graalvm-ce-java11-21.3.0/bin/native-image -jar target/mainModule-1.0-SNAPSHOT.jar --no-fallback
sudo mv -v mainModule-1.0-SNAPSHOT /opt/MagicScan/MagicScan-1.0
