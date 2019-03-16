#!/bin/bash
git status
git fetch origin

git branch master origin/master
git checkout -b  temp


CHANGED_FILES=`git diff --name-only origin/master`

for CHANGED_FILE in $CHANGED_FILES
    do
    IFS='/' read -r -a array <<< "$CHANGED_FILE"

    if [[ $CHANGED_FILE == *".java"* ]]; then

        # if last file change is in same subproject, ignore it
        if [ "$SUBPROJECT" != "${array[0]}:${array[1]}" ]; then
            SUBPROJECT="${array[0]}:${array[1]}"
            logger -s "Building $SUBPROJECT"
             ./gradlew ":$SUBPROJECT:build"
        fi
    fi
done
