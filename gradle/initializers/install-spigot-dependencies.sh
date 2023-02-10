#!/usr/bin/env bash

pushd "$(dirname "$BASH_SOURCE")" || exit

# $1: 버전
# $2: "remapped" 시 remapped 가 다운됨
buildSpigotIfMissing() {
  local classifier=""
  if [ "$2" = "remapped" ]; then
    classifier="-remapped-mojang";
  fi

  if [ ! -f "$HOME/.m2/repository/org/bukkit/craftbukkit/$1-R0.1-SNAPSHOT/craftbukkit-$1-R0.1-SNAPSHOT${classifier}.jar" ]; then
    bash install-spigot.sh "$1"
  else
    echo "메이븐에 이미 Spigot 이 존재하기 때문에 Spigot $1 을 빌드하지 않습니다."
  fi
}

buildSpigotIfMissing 1.8.8
buildSpigotIfMissing 1.9.4
buildSpigotIfMissing 1.12
buildSpigotIfMissing 1.16.1
buildSpigotIfMissing 1.16.5
popd